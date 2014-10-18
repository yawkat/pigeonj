package at.yawk.pigeon;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yawkat
 */
@SuppressWarnings("unchecked")
public class ItemHistory<T> {
    private final int chunkCount;
    private final int chunkSize;

    private final Chunk[] chunks;
    private final AtomicInteger currentChunk;

    public ItemHistory(int chunkCount, int chunkSize) {
        this.chunkCount = chunkCount;
        this.chunkSize = chunkSize;
        this.chunks = new Chunk[chunkCount];
        this.currentChunk = new AtomicInteger(0);
    }

    public ItemHistory() {
        this(2, 10000);
    }

    public boolean push(T object) {
        int start = currentChunk.get();
        int i = start;
        do {
            if (chunks[i].has(object)) {
                return false;
            }
            i = (i + 1) % chunkCount;
        } while (i != start);

        Chunk front = chunks[i];
        boolean added = front.push(object);
        if (added) {
            if (front.isExactFull() && currentChunk.get() == i) {
                shift();
            }
        }
        return added;
    }

    private void shift() {
        int next = (currentChunk.get() + 1) % chunkCount;
        chunks[next].clear();
        currentChunk.set(next);
    }

    @SuppressWarnings("TypeParameterHidesVisibleType")
    private class Chunk<T> { // need to use type parameter here to avoid "generic array creation" error above
        private final Set<T> content = Collections.newSetFromMap(new ConcurrentHashMap<>());

        public boolean has(T object) {
            return content.contains(object);
        }

        public boolean push(T object) {
            return content.add(object);
        }

        public boolean isExactFull() {
            return content.size() == chunkSize;
        }

        public void clear() {
            content.clear();
        }
    }
}
