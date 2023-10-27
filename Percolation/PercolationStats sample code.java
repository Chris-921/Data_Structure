package hw2;

public class PercolationStats {
    private double[] thresholds;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N OR T can not less than 1");
        }
        double sizeNN = N * N;
        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = (int) (Math.random() * N);
                int col = (int) (Math.random() * N);
                p.open(row, col);
            }
            double threshold = p.numberOfOpenSites() / sizeNN;
            thresholds[i] = threshold;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum_mean = 0.0;
        for (int i = 0; i < thresholds.length; i++) {
            sum_mean += thresholds[i];
        }
        return sum_mean / thresholds.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double sd = 0.0;
        for (int i = 0; i < thresholds.length; i++) {
            sd += Math.pow((thresholds[i] - mean()), 2);
        }
        sd /= (thresholds.length - 1);
        return Math.sqrt(sd);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(thresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(thresholds.length));
    }

}
