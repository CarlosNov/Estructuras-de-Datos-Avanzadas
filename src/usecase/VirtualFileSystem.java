package usecase;


import material.Position;
import material.tree.iterators.PreorderIterator;
import material.tree.narytree.LinkedTree;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class VirtualFileSystem
{
    private class VirtualFileNode
    {
        private String name;
        private String path;
        private int depth;
        private int id;

        public VirtualFileNode(String s, String p, int d, int id)
        {
            this.name = s;
            this.path = p;
            this.depth = d;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getDepth() {
            return depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    private LinkedTree<VirtualFileNode> tree = new LinkedTree<>();
    private ArrayList<Position<VirtualFileNode>> positions = new ArrayList<>();
    private int internalNodeID = positions.size();

    public void loadFileSystem(String path)
    {
        File rootFile = new File(path);

        resetVirtualFileSystem();

        VirtualFileNode root = new VirtualFileNode(rootFile.getName(), rootFile.getPath(), 0, positions.size());
        Position<VirtualFileNode> rootPosition = tree.addRoot(root);
        positions.add(rootPosition);

        findRecursiveFiles(rootFile, rootPosition);
    }

    private void findRecursiveFiles(File file, Position<VirtualFileNode> position)
    {
        for (final File f : file.listFiles())
        {
            if (f.isDirectory() && !isHidden(f))
            {
                VirtualFileNode node = new VirtualFileNode(f.getName(), f.getPath(),
                        position.getElement().getDepth()+1, positions.size());
                Position<VirtualFileNode> p = tree.add(node,position);
                positions.add(p);
                findRecursiveFiles(f, p);
            }

            if (f.isFile() && !isHidden(f))
            {
                VirtualFileNode node = new VirtualFileNode(f.getName(), f.getPath(),
                        position.getElement().getDepth()+1, positions.size());
                Position<VirtualFileNode> p = tree.add(node,position);
                positions.add(p);
            }
        }
    }

    private boolean isHidden(File file)
    {
        return file.getName().indexOf(".") == 0;
    }

    private void resetVirtualFileSystem()
    {
        tree = new LinkedTree<>();
        positions = new ArrayList<>();
    }

    public String getFileSystem()
    {
        Iterator<Position<VirtualFileNode>> it = new PreorderIterator<VirtualFileNode>(tree);
        String s = "";
        while (it.hasNext())
        {
            VirtualFileNode node = it.next().getElement();

            String aux = node.getId() + " ";
            for(int i= 0; i< node.getDepth(); i++)
            {
                aux = aux + "\t";
            }
            aux = aux + node.getName();
            aux = aux + "\n";
            s = s + aux;
        }
        System.out.println(s);
        return s;
    }

    public void moveFileById(int idFile, int idTargetFolder)
    {
        checkIfInsideTree(positions.get(idFile));
        checkIfInsideTree(positions.get(idTargetFolder));

        String path = positions.get(idTargetFolder).getElement().getPath();
        File file = new File(path);

        if(file.isFile())
        {
            throw new RuntimeException("Target can't be a file.");
        }

        try
        {
            tree.moveSubtree(positions.get(idFile), positions.get(idTargetFolder));
        }
        catch (Exception e)
        {
            if(e.getMessage().equals("Target position can't be a sub tree of origin"))
                throw new RuntimeException("A file can't be a subdirectory of itself.");

            if(e.getMessage().equals("The node is not from this tree"))
                throw new RuntimeException("Invalid ID.");
        }

        int newDepth = positions.get(idTargetFolder).getElement().getDepth()+1;
        updateDepth(positions.get(idFile), newDepth);
    }

    private void updateDepth(Position<VirtualFileNode> position, int depth)
    {
        Iterator<Position<VirtualFileNode>> it = new PreorderIterator<VirtualFileNode>(tree, position);

        while (it.hasNext())
        {
            Position<VirtualFileNode> pos = it.next();

            if(pos.equals(position))
            {
                VirtualFileNode prevNode = pos.getElement();
                VirtualFileNode newNode = new VirtualFileNode(prevNode.getName(), prevNode.getPath(), depth, prevNode.getId());
                tree.replace(position, newNode);
            }
            else {
                VirtualFileNode prevNode = pos.getElement();
                VirtualFileNode newNode = new VirtualFileNode(prevNode.getName(), prevNode.getPath(),
                        tree.parent(pos).getElement().getDepth() + 1, prevNode.getId());
                tree.replace(pos, newNode);
            }
        }
    }

    public void removeFileById(int idFile)
    {
        try
        {
            tree.remove(positions.get(idFile));
        }
        catch (Exception e)
        {
            if(e.getMessage().equals("The node is not from this tree"))
                throw new RuntimeException("Invalid ID.");
        }
    }

    public Iterable<String> findBySubstring(int idStartFile, String substring)
    {
        ArrayList<String> list = new ArrayList<>();

        Iterator<Position<VirtualFileNode>> it = new PreorderIterator<VirtualFileNode>(tree, positions.get(idStartFile));

        while (it.hasNext())
        {
            try
            {
                Position<VirtualFileNode> position = it.next();
                VirtualFileNode node = position.getElement();

                if(node.getName().indexOf(substring) != -1)
                {
                    list.add(node.getId()+"\t"+node.getName());
                }

            }catch (Exception e)
            {
                if(e.getMessage().equals("The node is not from this tree"))
                    throw new RuntimeException("Invalid ID.");
            }
        }
        return list;
    }

    public Iterable<String> findBySize(int idStartFile, long minSize, long maxSize)
    {
        if(maxSize < minSize)
            throw new RuntimeException("Invalid range.");

        ArrayList<String> list = new ArrayList<>();

        Iterator<Position<VirtualFileNode>> it = new PreorderIterator<VirtualFileNode>(tree, positions.get(idStartFile));

        while (it.hasNext())
        {
            try
            {
                Position<VirtualFileNode> position = it.next();
                VirtualFileNode node = position.getElement();

                File file = new File(position.getElement().getPath());

                if(file.isFile())
                    if(minSize <= file.length() && file.length() <= maxSize)
                        list.add(node.getId()+"\t"+node.getName());

            }catch (Exception e)
            {
                if(e.getMessage().equals("The node is not from this tree"))
                    throw new RuntimeException("Invalid ID.");
            }
        }
        return list;
    }

    public String getFileVirtualPath(int idFile)
    {
        checkIfInsideTree(positions.get(idFile));

        String virtualPath = "";
        Position<VirtualFileNode> position = positions.get(idFile);
        VirtualFileNode node = position.getElement();
        try
        {
            virtualPath = virtualPath + "/" + node.getName();

            Position<VirtualFileNode> parent = tree.parent(position);

            while (!tree.isRoot(position))
            {
                node = parent.getElement();
                virtualPath = "/" + node.getName() + virtualPath;

                position = tree.parent(position);
                parent = tree.parent(parent);

            }
        }
        catch (Exception e)
        {

        }
        finally {
            virtualPath = "vfs:" + virtualPath;
            return virtualPath;
        }
    }

    public String getFilePath(int idFile)
    {
        checkIfInsideTree(positions.get(idFile));
        return positions.get(idFile).getElement().getPath().replace("\\", "/");
    }

    private void checkIfInsideTree (Position<VirtualFileNode> position )throws RuntimeException
    {
        try
        {
            if (!tree.isRoot(position) || !tree.isLeaf(position) || !tree.isInternal(position)){}
        }
        catch (Exception e)
        {
            if(e.getMessage().equals("The node is not from this tree"))
                throw new RuntimeException("Invalid ID.");
        }
    }
}
