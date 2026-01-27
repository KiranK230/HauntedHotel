/* 
package com.group5.ai;

import com.group5.main.GamePanel;
import com.group5.tile.Tile;
import com.group5.tile.TileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathfinderTest {

    GamePanel gp;
    Pathfinder pf;

    @BeforeEach
    void setUp() {
        // ---- Minimal GamePanel stub ----
        gp = new GamePanel();
        gp.maxWorldCol = 5;
        gp.maxWorldRow = 5;
        gp.currentMap = 0;

        // TileManager stub
        gp.tileM = new TileManager(gp);
        gp.tileM.mapTilenum = new int[1][gp.maxWorldCol][gp.maxWorldRow];
        gp.tileM.tile = new Tile[10];

        // Create tiles (0 = walkable, 1 = solid)
        for (int i = 0; i < 10; i++) {
            gp.tileM.tile[i] = new Tile();
            gp.tileM.tile[i].collision = false;
        }

        gp.tileM.tile[1].collision = true; // solid tile

        pf = new Pathfinder(gp);
    }

    @Test
    void testInstantiateNodes() {
        assertNotNull(pf.node);
        assertEquals(5, pf.node.length);
        assertEquals(5, pf.node[0].length);

        assertEquals(0, pf.node[0][0].col);
        assertEquals(0, pf.node[0][0].row);
        assertEquals(4, pf.node[4][4].col);
        assertEquals(4, pf.node[4][4].row);
    }

    @Test
    void testResetNodes() {
        pf.node[2][2].open = true;
        pf.node[2][2].checked = true;
        pf.node[2][2].solid = true;

        pf.openList.add(pf.node[0][0]);
        pf.pathList.add(pf.node[4][4]);
        pf.goalReached = true;
        pf.step = 123;

        pf.resetNodes();

        assertFalse(pf.node[2][2].open);
        assertFalse(pf.node[2][2].checked);
        assertFalse(pf.node[2][2].solid);

        assertEquals(0, pf.openList.size());
        assertEquals(0, pf.pathList.size());
        assertFalse(pf.goalReached);
        assertEquals(0, pf.step);
    }

    @Test
    void testSetNodeAndCollisionMapping() {
        gp.tileM.mapTilenum[0][1][1] = 1; // solid tile at (1,1)

        pf.setNode(0, 0, 4, 4);

        assertEquals(pf.startNode, pf.node[0][0]);
        assertEquals(pf.goalNode, pf.node[4][4]);

        // Tile collision must transfer into node.solid
        assertTrue(pf.node[1][1].solid);
        // Goal must always be walkable even if tile was solid
        assertFalse(pf.node[4][4].solid);

        // Costs should be computed
        assertEquals(8, pf.node[0][0].hCost);
    }

    @Test
    void testGetCost() {
        pf.setNode(0, 0, 4, 4);

        Pathfinder.Node n = pf.node[2][2];
        pf.getCost(n);

        assertEquals(4, n.gCost);  // |2-0| + |2-0|
        assertEquals(4, n.hCost);  // |2-4| + |2-4|
        assertEquals(8, n.fCost);
    }

    @Test
    void testOpenNode() {
        pf.setNode(0, 0, 4, 4);

        Pathfinder.Node n = pf.node[1][0];
        assertFalse(n.open);

        pf.openNode(n);

        assertTrue(n.open);
        assertEquals(pf.startNode, n.parent);
        assertTrue(pf.openList.contains(n));
    }

    @Test
    void testSearchFindsSimplePath() {
        // Place a solid wall except one gap
        gp.tileM.mapTilenum[0][2][0] = 1;
        gp.tileM.mapTilenum[0][2][1] = 1;
        gp.tileM.mapTilenum[0][2][3] = 1;
        gp.tileM.mapTilenum[0][2][4] = 1;
        // (2,2) is the gap

        pf.setNode(0, 0, 4, 4);

        boolean reached = pf.search();
        assertTrue(reached);
        assertTrue(pf.goalReached);

        // Ensure a path exists
        assertFalse(pf.pathList.isEmpty());
        assertEquals(pf.node[4][4], pf.pathList.get(pf.pathList.size() - 1));

        // Ensure nodes lead from start → goal
        Pathfinder.Node prev = pf.startNode;
        for (Pathfinder.Node n : pf.pathList) {
            assertNotNull(n.parent);
            prev = n;
        }
    }

    @Test
    void testTrackThePath() {
        pf.setNode(0, 0, 2, 2);

        Pathfinder.Node a = pf.node[1][1];
        Pathfinder.Node b = pf.node[2][2];

        a.parent = pf.startNode;
        b.parent = a;

        pf.goalNode = b;
        pf.trackThePath();

        assertEquals(2, pf.pathList.size());
        assertEquals(a, pf.pathList.get(0));
        assertEquals(b, pf.pathList.get(1));
    }
}
*/
