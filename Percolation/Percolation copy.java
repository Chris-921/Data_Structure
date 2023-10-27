package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] board;
    private int size;
    private int sizeNN;

    private int virtrualTop;

    private int virtrualBottom;

    private WeightedQuickUnionUF unionT;

    private WeightedQuickUnionUF unionTB;


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should greater than 0");
        }
        board = new boolean[N][N];
        size = 0;
        sizeNN = N;
        virtrualTop = N * N;
        virtrualBottom = N * N + 1;
        unionT = new WeightedQuickUnionUF(N * N + 1);
        unionTB = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == 0) {
                    unionT.union(j, virtrualTop);
                    unionTB.union(j, virtrualTop);
                }
                if (i == N - 1) {
                    unionTB.union(i * sizeNN + j, virtrualBottom);
                }
                board[i][j] = false;
            }

        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        board[row][col] = true;
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            unionT.union(row * sizeNN + col, (row - 1) * sizeNN + col);
            unionTB.union(row * sizeNN + col, (row - 1) * sizeNN + col);
        }
        if (row + 1 < sizeNN && isOpen(row + 1, col)) {
            unionT.union(row * sizeNN + col, (row + 1) * sizeNN + col);
            unionTB.union(row * sizeNN + col, (row + 1) * sizeNN + col);
        }
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            unionT.union(row * sizeNN + col, row * sizeNN + (col - 1));
            unionTB.union(row * sizeNN + col, row * sizeNN + (col - 1));
        }
        if (col + 1 < sizeNN && isOpen(row, col + 1)) {
            unionT.union(row * sizeNN + col, row * sizeNN + (col + 1));
            unionTB.union(row * sizeNN + col, row * sizeNN + (col + 1));
        }
        size++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return board[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }
        return unionT.connected(row * sizeNN + col, virtrualTop);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return size;
    }

    // does the system percolate?
    public boolean percolates() {
        if (sizeNN == 1 && !isFull(0, 0)) {
            return false;
        }
        return unionTB.connected(virtrualTop, virtrualBottom);
    }
}
