import java.util.*;

// Memory Block class representing a block of memory
class MemoryBlock {
    int address;
    int size;
    boolean allocated;
    MemoryBlock next;

    public MemoryBlock(int address, int size) {
        this.address = address;
        this.size = size;
        this.allocated = false;
        this.next = null;
    }
}

// Dynamic Memory Allocator class
public class DynamicMemoryAllocator {
    MemoryBlock head; // Head of the free memory blocks list

    // Constructor
    public DynamicMemoryAllocator() {
        head = new MemoryBlock(0, Integer.MAX_VALUE); // Initialize with a single large free block
    }

    // Method to allocate memory using First Fit Split strategy
    public MemoryBlock firstFitSplit(int size) {
        MemoryBlock curr = head;
        MemoryBlock prev = null;
        while (curr != null) {
            if (!curr.allocated && curr.size >= size) {
                if (curr.size > size) {
                    MemoryBlock newBlock = new MemoryBlock(curr.address + size, curr.size - size);
                    newBlock.next = curr.next;
                    curr.next = newBlock;
                }
                curr.size = size;
                curr.allocated = true;
                return curr;
            }
            prev = curr;
            curr = curr.next;
        }
        return null; // Not enough memory available
    }

    // Method to allocate memory using Best Fit Split strategy
    public MemoryBlock bestFitSplit(int size) {
        MemoryBlock bestFitBlock = null;
        MemoryBlock curr = head;
        MemoryBlock prev = null;
        while (curr != null) {
            if (!curr.allocated && curr.size >= size) {
                if (bestFitBlock == null || curr.size < bestFitBlock.size) {
                    bestFitBlock = curr;
                }
            }
            prev = curr;
            curr = curr.next;
        }
        if (bestFitBlock != null) {
            if (bestFitBlock.size > size) {
                MemoryBlock newBlock = new MemoryBlock(bestFitBlock.address + size, bestFitBlock.size - size);
                newBlock.next = bestFitBlock.next;
                bestFitBlock.next = newBlock;
            }
            bestFitBlock.size = size;
            bestFitBlock.allocated = true;
        }
        return bestFitBlock;
    }

    // Method to deallocate memory
    public void free(MemoryBlock block) {
        block.allocated = false;
        // Merge adjacent free blocks
        MemoryBlock curr = head;
        while (curr != null && curr.next != null) {
            if (!curr.allocated && !curr.next.allocated) {
                curr.size += curr.next.size;
                curr.next = curr.next.next;
            }
            curr = curr.next;
        }
    }

    // Method to print memory layout
    public void printMemoryLayout() {
        MemoryBlock curr = head;
        System.out.println("Memory Layout:");
        while (curr != null) {
            System.out.println("Address: " + curr.address + ", Size: " + curr.size + ", Allocated: " + curr.allocated);
            curr = curr.next;
        }
    }

    public static void main(String[] args) {
        DynamicMemoryAllocator allocator = new DynamicMemoryAllocator();
        MemoryBlock allocatedBlock1 = allocator.firstFitSplit(50);
        MemoryBlock allocatedBlock2 = allocator.bestFitSplit(20);
        MemoryBlock allocatedBlock3 = allocator.firstFitSplit(30);
        MemoryBlock allocatedBlock4 = allocator.bestFitSplit(40);
        allocator.printMemoryLayout();

        // Deallocate memory
        allocator.free(allocatedBlock2);
        allocator.printMemoryLayout();
    }
}
