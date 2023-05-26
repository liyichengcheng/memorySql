package com.blue.sqldb.datastruct.baddtree;

public class Entity implements Comparable {
    public Long id;
    public int version;

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
