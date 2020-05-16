/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Connection;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Node;
import dk.sdu.mmmi.cbse.common.data.Path;
import java.util.HashMap;

/**
 *
 * @author rasmusstamm
 */
public class PathFinderPart implements EntityPart {

    private PQHeap pqHeap;
    private HashMap cheapMap;
    private GameData gameData;

    @Override
    public void process(GameData gameData, Entity entity) {

    }

    public Path findPath(Node initial, Node goal) {

        cheapMap = new HashMap();

        pqHeap = new PQHeap();

        Path path = new Path(initial, goal);

        while (!path.get(path.getNodeCount() - 1).equals(goal)) {
            // add path to gameData for debug rendering
            if (gameData != null) {
                gameData.getPathingDebugList().add(path);
            }
            expand(path);
            path = pqHeap.extractMin();
        }
        return path;
    }

    private void expand(Path path) {

        Node node = path.get(path.getNodeCount() - 1);

        for (Connection con : node.getConnections()) {
            Path newPath = new Path(path);
            Node newNode = ((Node) con.getToNode());

            if (!cheapMap.containsKey(newNode.getIndex()) || (float) cheapMap.get(newNode.getIndex()) > (newPath.getF() + 1)) {

                newPath.addConnection(con);

                cheapMap.put(newNode.getIndex(), newPath.getF());

                pqHeap.insert(newPath);
            }
        }
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }
    

    private class PQHeap {

        private Array<Path> minHeap = new Array<Path>();

        public Path extractMin() {
            Path min = minHeap.get(0);

            minHeap.set(0, minHeap.get(minHeap.size - 1));
            minHeap.removeIndex(minHeap.size - 1);

            minHeapify(0);

            return min;
        }

        public void insert(Path path) {
            minHeap.add(path);

            int i = minHeap.size - 1;

            while (i > 0 && minHeap.get(parent(i)).getF() > minHeap.get(i).getF()) {
                swap(i, parent(i));
                i = parent(i);
            }
        }

        private void minHeapify(int i) {
            int l = left(i);
            int r = right(i);

            int smallest;

            if (l <= minHeap.size - 1 && minHeap.get(l).getF() < minHeap.get(i).getF()) {
                smallest = l;
            } else {
                smallest = i;
            }
            if (r <= minHeap.size - 1 && minHeap.get(r).getF() < minHeap.get(smallest).getF()) {
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
            return (i - 1) / 2;
        }

        private void swap(int a, int b) {
            Path temp;

            temp = minHeap.get(a);
            minHeap.set(a, minHeap.get(b));
            minHeap.set(b, temp);
        }
    }
}

