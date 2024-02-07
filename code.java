// MemoryBlock.java
public class MemoryBlock {
    private int address;
    private int size;
    private boolean allocated;

    public MemoryBlock(int address, int size) {
        this.address = address;
        this.size = size;
        this.allocated = false;
    }

    public int getAddress() {
        return address;
    }

    public int getSize() {
        return size;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void allocate() {
        allocated = true;
    }

    public void deallocate() {
        allocated = false;
    }

    public void resize(int newSize) {
        size = newSize;
    }
}
