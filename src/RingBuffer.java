public class RingBuffer<T> {

    private int front;
    private int size;
    private Object[] ringBuffer;

    public RingBuffer(int length){
        front = 0;
        size = 0;
        ringBuffer = new Object[length];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean isFull(){
        return size == ringBuffer.length;
    }

    public void enqueue(T element){
        if(isFull()){
            throw new IllegalStateException("Queue is full");
        }

        ringBuffer[(front + size) % ringBuffer.length] = element;
        size++;
    }

    //moves all elements of another queue to this queue
    public void enqueue(RingBuffer<T> r2){
        if(isFull()){
            throw new IllegalStateException("Queue is full");
        }
        else if(size + r2.size() > ringBuffer.length) throw new IllegalStateException("Queue is too long.");

        while(!r2.isEmpty()){
            ringBuffer[(front + size) % ringBuffer.length] = r2.dequeue();
            size++;
        }
    }

    public T dequeue(){
        if(isEmpty()){
            throw new IllegalStateException("Queue is empty");
        }

        T store = (T) ringBuffer[front];

        front = (front + 1) % ringBuffer.length;

        size--;
        return store;
    }

    public T peek(){
        if(isEmpty()){
            throw new IllegalStateException("Queue is empty");
        }

        return (T) ringBuffer[front];
    }

    @Override
    public String toString() {
        String queue = "";
        for (int i = 0; i < size(); i++) {
            queue += ringBuffer[(front + i) % ringBuffer.length] + " ";
        }

        return queue;
    }
}