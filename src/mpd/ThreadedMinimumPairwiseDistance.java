package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {     
      
   int globalResult = Integer.MAX_VALUE; 
   private int[] values;

    @Override
    public int minimumPairwiseDistance(int[] values){
        this.values = values;

        lowerLeft ll = new lowerLeft();
        lowerRight lr = new lowerRight();
        topRight tr = new topRight();
        center c = new center();


        Thread lowerLeft = new Thread(ll);
        Thread lowerRight = new Thread(lr);
        Thread topRight = new Thread(tr);
        Thread center = new Thread(c);

        lowerLeft.start();
        lowerRight.start();
        topRight.start();
        center.start();

        try {
              lowerLeft.join();
	      lowerRight.join();
	      topRight.join();
              center.join();
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block  
	        e.printStackTrace();
                }

        //updateGlobalResult(localResult);
        return globalResult;

    }

    public synchronized int updateGlobalResult(int result){
        if(globalResult > result){
                globalResult = result;
        }
        return globalResult;
    }


private class lowerLeft implements Runnable{

        @Override
        public void run() {
                int localResult = Integer.MAX_VALUE;
        for (int i = 0; i < values.length/2; ++i) {
            for (int j = 0; j < i; ++j) {
                // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                int diff = Math.abs(values[i] - values[j]);
                if (diff < localResult) {
                    localResult = diff;
                }
            }
        }
        updateGlobalResult(localResult);
        }
 }

private class lowerRight implements Runnable{

        @Override
        public void run() {
                int localResult = Integer.MAX_VALUE;
        for (int i = values.length/2; i < values.length; ++i) {
            for (int j = 0; j < i-values.length/2; ++j) {
                // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                int diff = Math.abs(values[i] - values[j]);
                if (diff < localResult) {
                    localResult = diff;
                }
            }
        }
        updateGlobalResult(localResult);
        }
 }

private class topRight implements Runnable{

        @Override
        public void run() {
                int localResult = Integer.MAX_VALUE;
        for (int i = values.length/2; i < values.length; ++i) {
            for (int j = values.length/2; j < i; ++j) {
                // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                int diff = Math.abs(values[i] - values[j]);
                if (diff < localResult) {
                    localResult = diff;
                }
            }
        }
        updateGlobalResult(localResult);
        }
 }

private class center implements Runnable{

        @Override
        public void run() {
                int localResult = Integer.MAX_VALUE;
        for (int j = 0; j < values.length/2; j++) {
            for (int i = values.length/2; i < j+values.length/2; ++i) {
                // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                int diff = Math.abs(values[i] - values[j]);
                if (diff < localResult) {
                    localResult = diff;
                }
            }
        }
        updateGlobalResult(localResult);
        }
 }
}
