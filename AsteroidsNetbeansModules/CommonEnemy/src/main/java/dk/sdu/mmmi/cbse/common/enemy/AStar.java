/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.enemy;

import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Connection;
import dk.sdu.mmmi.cbse.common.data.Heuristic;
import dk.sdu.mmmi.cbse.common.data.Node;
import dk.sdu.mmmi.cbse.common.data.Path;
import java.util.ArrayList;

/**
 *
 * @author rasmusstamm
 */
public class AStar {
    private Path fringe;
    private Node initial;
    private Node current;
    private Node goal;
    private PQHeap pqHeap;
    
    public Path doTheThing(Node initial, Node goal) {
        
        Path og = new Path(initial);
        
        for (Connection con : initial.getConnections()) {
            Path newPath = og; 
            newPath.addNode((Node) con.getToNode());
            
            pqHeap.insert(newPath);
        }
        
        return null;
    }
    
    private class PQHeap {
        private Array<Path> minHeap;
        
        public Path extractMin(){
            Path min = minHeap.get(0);
            
            minHeap.set(0, minHeap.get(minHeap.size - 1));
            minHeap.removeIndex(minHeap.size - 1);
            
            minHeapify(0);
            
            return min;
        }
        
        public void insert(Path path) {
            minHeap.add(path);
            
            int i = minHeap.size - 1;
            
            while(i > 0 && minHeap.get(parent(i)).getF()> minHeap.get(i).getF()) {
                swap(i, parent(i));
                i = parent(i);
            }
        } 

        private void minHeapify(int i) {
            int l = left(i);
            int r = right(i);
            
            int smallest;
            
            if (l <= minHeap.size - 1 && minHeap.get(l).getF()< minHeap.get(i).getF()) {
                smallest = l;
            } else {
                smallest = i;
            }
            if (r <= minHeap.size - 1 && minHeap.get(r).getF()< minHeap.get(smallest).getF()) {
                smallest = r;
            }
            
            if (smallest != i) {
                swap(i, smallest);
                minHeapify(smallest);
            }
            
        }

        private int left(int i) {
            return 2 * i + 1; 
        }

        private int right(int i) {
            return 2 * i + 2;
        }
        
        private int parent(int i) {
            return (i-1) / 2;
        }

        private void swap(int a, int b) {
            Path temp;
            
            temp = minHeap.get(a);
            minHeap.set(a, minHeap.get(b));
            minHeap.set(b, temp);
        }
    }
}
