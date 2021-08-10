package implementations;

import interfaces.AbstractTree;

import java.util.*;

public class Tree<E> implements AbstractTree<E> {
    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key/*, Tree<E>... children*/) {
        this.key = key;
        this.children = new ArrayList<>();
        /*for (Tree<E> child : children) {
            this.children.add(child);
            child.setParent(this);
        }*/

    }


    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {
        StringBuilder builder = new StringBuilder();

        traverseTreeWithRecurrence(builder, 0, this);

        return builder.toString().trim();
    }

    public List<Tree<E>> traverseWithBfs() {

        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);


        List<Tree<E>> allNodes = new ArrayList<>();

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            allNodes.add(current);


            for (Tree<E> child : current.children) {
                queue.offer(child);
            }

        }
        return allNodes;
    }

    private void traverseTreeWithRecurrence(StringBuilder builder, int indent, Tree<E> tree) {

        builder.append(this.getPadding(indent))
                .append(tree.getKey())
                .append(System.lineSeparator());

        for (Tree<E> child : tree.children) {
            traverseTreeWithRecurrence(builder, indent + 2, child);
        }


    }

    private String getPadding(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    @Override
    public List<E> getLeafKeys() {
        List<E> list = new ArrayList<>();

        getLeafsWithDfs(this, list);
        return list;
    }

    private void getLeafsWithDfs(Tree<E> tree, List<E> list) {

        for (Tree<E> child : tree.children) {
            getLeafsWithDfs(child, list);
        }

        if (tree.children.size() == 0) {
            list.add(tree.getKey());
        }

    }

    @Override
    public List<E> getMiddleKeys() {
        List<E> list = new ArrayList<>();

        getMiddleKeysWithDfs(this, list);
        return list;
    }

    private void getMiddleKeysWithDfs(Tree<E> tree, List<E> list) {
        for (Tree<E> child : tree.children) {
            getMiddleKeysWithDfs(child, list);
        }

        if (tree.children.size() > 0 && tree.getParent() != null) {
            list.add(tree.getKey());
        }
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> trees = this.traverseWithBfs();//чрез BFS

        int maxPath = 0;
        Tree<E> deepestLefMostNode = null;


        for (Tree<E> tree : trees) {
            if (tree.isLeaf()) {
                int currentPath = getStepsFromLeafToRoot(tree);
                if (currentPath > maxPath) {
                    maxPath = currentPath;
                    deepestLefMostNode = tree;
                }
            }
        }
        return deepestLefMostNode;//чрез BFS


    }


    private int getStepsFromLeafToRoot(Tree<E> tree) {
        int counter = 0;
        Tree<E> current = tree;

        while (current.parent != null) {
            counter++;
            current = current.parent;
        }
        return counter;
    }

    private boolean isLeaf() {
        return this.parent != null && this.children.size() == 0;
    }

    @Override
    public List<E> getLongestPath() {
        Tree<E> current = this.getDeepestLeftmostNode();

        List<E> list = new ArrayList<>();

        while (true) {
            list.add(current.getKey());
            if (current.parent == null) {
                break;
            }
            current = current.parent;

        }
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        List<List<E>> list = new ArrayList<>();
        int currentSum = 0;
        method(sum, list, currentSum, this);

        return list;
    }

    private void method(int sum, List<List<E>> list, int currentSum, Tree<E> tree) {
        currentSum += (int) tree.getKey();

        for (Tree<E> child : tree.children) {
            method(sum, list, currentSum, child);
        }

        if (tree.isLeaf() && currentSum == sum) {
            List<E> currentList = new ArrayList<>();
            Tree<E> tree1 = tree;
            while (true) {
                currentList.add(tree1.getKey());
                if (tree1.parent == null) {
                    break;
                }
                tree1 = tree1.parent;

            }
            Collections.reverse(currentList);
            list.add(currentList);

        }
        currentSum -= (int) tree.getKey();



    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        List<Tree<E>> trees = this.traverseWithBfs();
        List<Tree<E>> subTrees = new ArrayList<>();
        for (Tree<E> tree : trees) {

            if (tree.isLeaf() || tree.getParent() == null){
                continue;
            }

            int currentSum = 0;
            List<E> list = new ArrayList<>();
            calculateSum(list, tree);
            for (E e : list) {
                currentSum+=(int) e;
            }

            if (currentSum == sum){
                subTrees.add(tree);
            }

        }


        return subTrees;
    }

    private void calculateSum(List<E> list, Tree<E> tree) {


        for (Tree<E> child : tree.children) {
            calculateSum(list, child);

        }

        list.add(tree.getKey());




    }
}



