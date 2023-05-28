package com.blue.sqldb.datastruct.baddtree;

public class Entity implements Comparable {
    public Long id;
    public int version = 0;

    public int ops = 0;// -1表示删除，1 表示新增，2 表示 update
    public static final int ops_delete = -1;// -1表示删除，1 表示新增，2 表示 update
    public static final int ops_insert = 1;// -1表示删除，1 表示新增，2 表示 update
    public static final int ops_update = 2;// -1表示删除，1 表示新增，2 表示 update
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Entity(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getOps() {
        return ops;
    }

    public void setOps(int ops) {
        this.ops = ops;
    }

    @Override
    public int compareTo(Object o) {
        Entity other = (Entity)o;
        if(this.getId().longValue() == other.getId().longValue()) {
            return 0;
        }
        if(this.getId().longValue() > other.getId().longValue()) {
            return 1;
        } else {
            return -1;
        }
    }
}
