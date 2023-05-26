package com.blue.sqldb.datastruct.index;

import com.blue.sqldb.datastruct.baddtree.AvlTree;
import com.blue.sqldb.datastruct.baddtree.Entity;

import java.util.LinkedList;
import java.util.List;

public class SecondIndex<E extends Entity> {
    class Node {
        Long score;//排序关键字 可以重复
        AvlTree<Entity> valueList;
        Node left;
        Node right;
        SecondIndex.Node parent;//指向
        int height;

        public Node() {
        }

        public Node(Long score, AvlTree<Entity> valueList) {
            this.score = score;
            this.valueList = valueList;
            height = 1;
            left = null;
            right = null;
        }
    }

    Node root;
    int size;

    public int size() {
        return size;
    }

    public int getHeight(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // 获取平衡因子(左右子树的高度差，大小为1或者0是平衡的，大小大于1不平衡)
    public int getBalanceFactor() {
        return getBalanceFactor(root);
    }

    public int getBalanceFactor(Node node) {
        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    // 判断一个树是否是一个平衡二叉树
    public boolean isBalance(Node node) {
        if (node == null)
            return true;
        int balanceFactor = Math.abs(getBalanceFactor(node.left) - getBalanceFactor(node.right));
        if (balanceFactor > 1)
            return false;
        return isBalance(node.left) && isBalance(node.right);
    }

    public boolean isBalance() {
        return isBalance(root);
    }

    // 中序遍历树
    private void inPrevOrder(Node root) {
        if (root == null)
            return;
        inPrevOrder(root.left);
        inPrevOrder(root.right);
    }

    public void inPrevOrder() {
        inPrevOrder(root);
    }

    // 左旋,并且返回新的根节点
    public Node leftRotate(Node node) {
        SecondIndex.Node cur = node.right;
        SecondIndex.Node curLeft = cur.left;
        cur.left = node;
        cur.parent = node.parent;
        if(root == node) {
            root = cur;
        }
        node.parent = cur;
        node.right = curLeft;
        if (curLeft != null) {
            curLeft.parent = node;
        }
        // 跟新node和cur的高度
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        cur.height = Math.max(getHeight(cur.left), getHeight(cur.right)) + 1;
        return cur;
    }

    // 右旋，并且返回新的根节点
    public Node rightRotate(Node node) {
        SecondIndex.Node cur = node.left;
        SecondIndex.Node curRight = cur.right;
        cur.right = node;
        cur.parent = node.parent;
        if(root == node) {
            root = cur;
        }
        node.parent = cur;
        node.left = curRight;
        if (curRight != null) {
            curRight.parent = node;
        }
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        cur.height = Math.max(getHeight(cur.left), getHeight(cur.right)) + 1;
        return cur;
    }

    // 添加元素
    public void add(Long score, E value) {
        root = add(root, score, value);
    }

    private Node add(Node node, Long score, E value) {
        if (node == null) {
            size++;
            AvlTree<Entity> valueList = new AvlTree<Entity>();
            valueList.add(value.getId(),value);
            return new Node(score, valueList);
        }
        if(node.score == null){
            return null;
        }
        boolean needRelance = true;
        if (score.compareTo(node.score) > 0) {
            SecondIndex.Node rightNode = add(node.right, score, value);
            node.right = rightNode;
            rightNode.parent = node;
        } else if (score.compareTo(node.score) < 0) {
            SecondIndex.Node leftNode = add(node.left, score, value);
            node.left = leftNode;
            leftNode.parent = node;
        } else {
            node.valueList.add(value.getId(),value);
            needRelance = false;
        }
        if(needRelance) {
            // 跟新节点高度
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            // 获取当前节点的平衡因子
            int balanceFactor = getBalanceFactor(node);
            // 该子树不平衡且新插入节点(导致不平衡的节点)在左子树的左子树上，此时需要进行右旋
            if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
                return rightRotate(node);
            }
            // 该子树不平衡且新插入节点(导致不平衡的节点)在右子树子树的右子树上，此时需要进行左旋
            else if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
                return leftRotate(node);
            }
            // 该子树不平衡且新插入节点(导致不平衡的节点)在左子树的右子树上，此时需要先对左子树左旋，在整个树右旋
            else if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
            // balanceFactor < -1 && getBalanceFactor(node.left) > 0
            // 该子树不平衡且新插入节点(导致不平衡的节点)在右子树的左子树上，此时需要先对右子树右旋，再整个树左旋
            else if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }

    // 删除节点
    public int remove(Long score,E value) {
        Node node = remove(root,score,value);
        if (node == null) {
            return 0;
        }
        return 1;
    }

    public Node remove(Node node,Long score,E value) {
        if (node == null)
            return null;
        if (score.compareTo(node.score) > 0) {
            remove(node.right,score,value);
        } else if (score.compareTo(node.score) < 0) {
            remove(node.left,score,value);
        } else {
            if(null != node.valueList) {
                node.valueList.remove(value);
            }
            if(null == node.valueList || (node.valueList.size() == 0)) {
                SecondIndex.Node parent = node.parent;
                SecondIndex.Node leftNode = node.left;
                SecondIndex.Node rightNode = node.right;
                //左节点为空
                if (leftNode == null) {
                    if (parent != null && parent.left == node) {
                        parent.left = node.right;
                    }
                    if (parent != null && parent.right == node) {
                        parent.right = node.right;
                    }
                }
                // 右节点为空
                else if (rightNode == null) {
                    if (parent != null && parent.left == node) {
                        parent.left = node.left;
                    }
                    if (parent != null && parent.right == node) {
                        parent.right = node.left;
                    }
                }
                // 左右节点都不为空
                else {
                    // 寻找右子树最小的节点
                    SecondIndex.Node replaceNode = rightNode;
                    if (replaceNode.left != null) {
                        while (replaceNode.left != null) {
                            replaceNode = replaceNode.left;
                        }
                    }

                    if (replaceNode != rightNode) {
                        SecondIndex.Node replaceRightNode = replaceNode.right;
                        SecondIndex.Node replaceParentNode = replaceNode.parent;
                        replaceNode.right = rightNode;
                        replaceNode.left = leftNode;
                        replaceNode.parent = parent;
                        rightNode.parent = replaceNode;
                        replaceParentNode.left = replaceRightNode;
                        if (replaceRightNode != null) {
                            replaceRightNode.parent = replaceParentNode;
                        }
                    } else {
                        replaceNode.left = leftNode;
                        replaceNode.parent = parent;
                    }
                    if (parent != null && parent.left == node) {
                        parent.left = replaceNode;
                    }
                    if (parent != null && parent.right == node) {
                        parent.right = replaceNode;
                    }
                    leftNode.parent = replaceNode;
                    if (node == root) {
                        root = replaceNode;
                    }
                }
                node.parent = node.left = node.right = null;
                size--;
                resetHeight(root);
            }
        }
        return node;
    }

    public int resetHeight(SecondIndex.Node node) {
        if (node == null) {
            return 0;
        } else {
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        }
        return node.height;

    }

    public List<Entity> getList(Long score) {
        return getAvlTree(score,root).getAll();
    }
    public AvlTree<Entity> getAvlTree(Long score) {
        return getAvlTree(score,root);
    }
    private AvlTree<Entity> getAvlTree(Long score, Node cur) {
        while (cur != null) {
            if (cur.score < score) {
                cur = cur.right;
            } else if (cur.score.intValue() == score.intValue()) {
                return cur.valueList;
            } else {
                cur = cur.left;
            }
        }
        return null;
    }

    public List<Entity> getList(long fromScore,boolean includeFrom,long toScore,boolean includeTo) {
        if (null == root) {
            return null;
        }
        List<Entity> entityList = new LinkedList<Entity>();
        return getList(entityList,root,fromScore,includeFrom,toScore,includeTo);
    }
    private List<Entity> getList(List<Entity> entityList,Node cur,long fromScore,boolean includeFrom,long toScore,boolean includeTo) {
        long nodeScore = cur.score.longValue();
        boolean fromMatch = false;
        if (includeFrom) {
            fromMatch = (fromScore <= nodeScore);
        } else {
            fromMatch = (fromScore < nodeScore);
        }
        boolean toMatch = false;
        if (includeTo) {
            toMatch = (nodeScore <= toScore);
        } else {
            toMatch = (nodeScore < toScore);
        }
        if (fromMatch && toMatch) {
            entityList.addAll(cur.valueList.getAll());
            getList(entityList,cur.left,fromScore,includeFrom,toScore,includeTo);
            getList(entityList,cur.right,fromScore,includeFrom,toScore,includeTo);
        } else {
            if (fromMatch && null != cur.right) {
                getList(entityList,cur.right,fromScore,includeFrom,toScore,includeTo);
            }
            if (toMatch && null != cur.left) {
                getList(entityList,cur.left,fromScore,includeFrom,toScore,includeTo);
            }
        }
        return entityList;
    }

    public int update(Long score,E t) {
        AvlTree<Entity> entityList = getAvlTree(score);
        entityList.update(t);
        return 1;
    }
}