package com.blue.sqldb.datastruct.baddtree;

import java.util.LinkedList;
import java.util.List;

public class AvlTree<E extends Entity> {
    class Node {
        Long score;//排序关键字 不可以重复
        Entity value;
        Node left;
        Node right;
        Node parent;//指向
        int height;

        public Node() {
        }

        public Node(Long score, Entity value) {
            this.score = score;
            this.value = value;
            height = 1;
            left = null;
            right = null;
            parent = null;
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

    public int resetHeight(Node node) {
        if (node == null) {
            return 0;
        } else {
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        }
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
        Node cur = node.right;
        Node curLeft = cur.left;
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
        Node cur = node.left;
        Node curRight = cur.right;
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
            return new Node(score, value);
        }
        if (score.compareTo(node.score) > 0) {
            Node rightNode = add(node.right, score, value);
            node.right = rightNode;
            rightNode.parent = node;
        } else if (score.compareTo(node.score) < 0) {
            Node leftNode = add(node.left, score, value);
            node.left = leftNode;
            leftNode.parent = node;
        } else {
            throw new UnsupportedOperationException("score must be unique");
        }
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
        return node;
    }

    // 删除节点
//    public Entity remove(E value) {
//        root = remove(root, value);
//        if (root == null) {
//            return null;
//        }
//        return root.value;
//    }

    public Node remove(E value) {
        Node targetNode = root;
        while (targetNode != null) {
            if (value.compareTo(targetNode.value) > 0) {
                targetNode = targetNode.right;
            } else if (value.compareTo(targetNode.value) < 0) {
                targetNode = targetNode.left;
            } else {
                break;
            }
        }
        if (targetNode != null) {
            Node parent = targetNode.parent;
            Node leftNode = targetNode.left;
            Node rightNode = targetNode.right;
            //左节点为空
            if (leftNode == null) {
                if (parent != null && parent.left == targetNode) {
                    parent.left = targetNode.right;
                }
                if (parent != null && parent.right == targetNode) {
                    parent.right = targetNode.right;
                }
            }
            // 右节点为空
            else if (rightNode == null) {
                if (parent != null && parent.left == targetNode) {
                    parent.left = targetNode.left;
                }
                if (parent != null && parent.right == targetNode) {
                    parent.right = targetNode.left;
                }
            }
            // 左右节点都不为空
            else {
                // 寻找右子树最小的节点
                Node replaceNode = rightNode;
                if (replaceNode.left != null) {
                    while (replaceNode.left != null) {
                        replaceNode = replaceNode.left;
                    }
                }

                if (replaceNode != rightNode) {
                    Node replaceRightNode = replaceNode.right;
                    Node replaceParentNode = replaceNode.parent;
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
                if (parent != null && parent.left == targetNode) {
                    parent.left = replaceNode;
                }
                if (parent != null && parent.right == targetNode) {
                    parent.right = replaceNode;
                }
                leftNode.parent = replaceNode;
                if (targetNode == root) {
                    root = replaceNode;
                }
            }
            targetNode.parent = targetNode.left = targetNode.right = null;
            size--;
            resetHeight(root);
        }

        return null;
    }

    public Entity getOne(Long score) {
        Node cur = root;
        while (cur != null) {
            if (cur.score < score) {
                cur = cur.right;
            } else if (cur.score.intValue() == score.intValue()) {
                return cur.value;
            } else {
                cur = cur.left;
            }
        }
        return null;
    }

    public List<Entity> getList(Long score) {
        return getList(score,null,null);
    }
    private List<Entity> getList(Long score, Node cur, List<Entity> entityList) {
        if (cur == null) {
            entityList = new LinkedList<Entity>();
            cur = root;
        }
        while (cur != null) {
            if (cur.score < score) {
                cur = cur.right;
            } else if (cur.score.intValue() == score.intValue()) {
                entityList.add(cur.value);
                if (cur.right != null && cur.right.score.intValue() == score.intValue()) {
                    getList(score, cur.right, entityList);
                }
                if (cur.left != null && cur.left.score.intValue() == score.intValue()) {
                    getList(score, cur.left, entityList);
                }
                return entityList;
            } else {
                cur = cur.left;
            }
        }
        return null;
    }

    public List<Entity> getAll() {
        List<Entity> entityList = new LinkedList<Entity>();
        return getAll(root,entityList);
    }

    private List<Entity> getAll(Node cur,List<Entity> entityList) {
        if (cur == null) {
            cur = root;
        }
        entityList.add(cur.value);
        if(cur.left != null) {
            getAll(cur.left,entityList);
        }
        if(cur.right != null) {
            getAll(cur.right,entityList);
        }
        return entityList;
    }

    public int update(E t) {
        Long id = t.getId();
        Node cur = root;
        while (cur != null) {
            if (cur.value.getId() < id) {
                cur = cur.right;
            } else if (cur.value.getId() == id) {
                cur.value = t;
                return 1;
            } else {
                cur = cur.left;
            }
        }
        return 0;
    }
}