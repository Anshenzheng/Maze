
import java.util.ArrayList;
import java.util.List;

public class MazeWay {

    private static Position target = new Position(2, 3);
    private static int[][] maze = {
            //1 - stands for wall
            //0 - stands for path
            //0  1  2  3  4  5
            {1, 1, 1, 1, 1, 1},//row_y = 0
            {1, 0, 0, 0, 0, 1},//row_y = 1
            {1, 0, 1, 0, 0, 1},//row_y = 2
            {1, 0, 0, 1, 0, 1},//row_y = 3
            {1, 1, 0, 0, 0, 1},//row_y = 4
            {1, 0, 0, 0, 0, 1},//row_y = 5
            {1, 1, 1, 1, 1, 1} //row_y = 6
    };

    public static void main(String[] args) {
        broadFirstTrace();
        depthFirstTrace();
    }

    /*
     * Broad-First trace
     * */
    private static void broadFirstTrace() {
        //init
        MarkBall begin = new MarkBall(1, 1, null);
        Boolean[][] marked = new Boolean[maze.length][maze[0].length];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                marked[i][j] = false;
            }
        }
        marked[1][1] = true;
        List<MarkBall> traces = new ArrayList<>();
        List<MarkBall> finalTraces = new ArrayList<>();
        traces.add(begin);

        //print maze
        printMaze(maze, target.x, target.y);

        //trace to target
        markTrace(traces, marked, finalTraces);

        //print possible trace
        if (finalTraces.isEmpty()) {
            System.out.println("No way to target");
        } else {
            finalTraces.stream().forEach(MazeWay::printMarkBall);
        }
    }

    /*
    * Depth-first trace
    * */
    private static void depthFirstTrace(){

        Position currentPosition = new Position(1,1);
        MarkBall now = new MarkBall(1,1,null);

        Boolean[][] marked = new Boolean[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                marked[i][j] = false;
            }
        }
        marked[1][1] = true;
        while(true){
            //mark right
            if(!marked[currentPosition.y][currentPosition.x + 1] && maze[currentPosition.y][currentPosition.x + 1] != 1){
                now = new MarkBall(currentPosition.x + 1,currentPosition.y,now);
                marked[currentPosition.y][currentPosition.x + 1] = true;
                currentPosition.x = currentPosition.x+1;
            }else
            //mark down
            if(!marked[currentPosition.y+1][currentPosition.x] && maze[currentPosition.y+1][currentPosition.x] != 1){
                now = new MarkBall(currentPosition.x,currentPosition.y+1,now);
                marked[currentPosition.y+1][currentPosition.x] = true;
                currentPosition.y = currentPosition.y+1;
            }else
            //mark left
            if(!marked[currentPosition.y][currentPosition.x - 1] && maze[currentPosition.y][currentPosition.x-1] != 1){
                now = new MarkBall(currentPosition.x-1,currentPosition.y,now);
                marked[currentPosition.y][currentPosition.x - 1] = true;
                currentPosition.x = currentPosition.x-1;
            }else
            //mark up
            if(!marked[currentPosition.y-1][currentPosition.x] && maze[currentPosition.y-1][currentPosition.x] != 1){
                now = new MarkBall(currentPosition.x,currentPosition.y-1,now);
                marked[currentPosition.y-1][currentPosition.x] = true;
                currentPosition.y = currentPosition.y-1;
            }else{
                now = now.father;
                if(now == null){
                    System.out.println("No way to target");
                    break;
                }else{
                    currentPosition.x = now.x;
                    currentPosition.y = now.y;
                }
            }

            if(now.x == target.x && now.y == target.y){
                printMarkBall(now);
                break;
            }
        }
    }

    private static void markTrace(List<MarkBall> traces, Boolean[][] marked, List<MarkBall> finalTraces) {
        MarkBall rightBall, leftBall, upBall, downBall;
        for (int i = 0; i < traces.size(); i++) {
            MarkBall markBall = traces.get(i);
            //mark right
            if (!marked[markBall.y][markBall.x + 1] && maze[markBall.y][markBall.x + 1] == 0) {
                rightBall = new MarkBall(markBall.x + 1, markBall.y, markBall);
                marked[markBall.y][markBall.x + 1] = true;
                if (rightBall.x == target.x && rightBall.y == target.y) {
                    finalTraces.add(rightBall);
                    marked[target.y][target.x] = false;
                } else {
                    traces.add(rightBall);
                }
            }
            //mark left
            if (!marked[markBall.y][markBall.x - 1] && maze[markBall.y][markBall.x - 1] == 0) {
                leftBall = new MarkBall(markBall.x - 1, markBall.y, markBall);
                marked[markBall.y][markBall.x - 1] = true;
                if (leftBall.x == target.x && leftBall.y == target.y) {
                    finalTraces.add(leftBall);
                    marked[target.y][target.x] = false;
                } else {
                    traces.add(leftBall);
                }
            }
            //mark up
            if (!marked[markBall.y - 1][markBall.x] && maze[markBall.y - 1][markBall.x] == 0) {
                upBall = new MarkBall(markBall.x, markBall.y - 1, markBall);
                marked[markBall.y - 1][markBall.x] = true;
                if (upBall.x == target.x && upBall.y == target.y) {
                    finalTraces.add(upBall);
                    marked[target.y][target.x] = false;
                } else {
                    traces.add(upBall);
                }
            }
            //mark down
            if (!marked[markBall.y + 1][markBall.x] && maze[markBall.y + 1][markBall.x] == 0) {
                downBall = new MarkBall(markBall.x, markBall.y + 1, markBall);
                marked[markBall.y + 1][markBall.x] = true;
                if (downBall.x == target.x && downBall.y == target.y) {
                    finalTraces.add(downBall);
                    marked[target.y][target.x] = false;
                } else {
                    traces.add(downBall);
                }
            }
            traces.remove(markBall);
        }

        if (!traces.isEmpty()) {
            markTrace(traces, marked, finalTraces);
        }
    }

    private static void printMarkBall(MarkBall markBall) {
        String line = null;
        while (true) {
            if (markBall.father != null) {
                if (line == null) {
                    line = "(" + markBall.x + "," + markBall.y + ")";
                } else {
                    line = "(" + markBall.x + "," + markBall.y + ") -> " + line;
                }
                markBall = markBall.father;
            } else {
                break;
            }
        }
        System.out.println("(1,1) -> " + line);
    }

    private static void printMaze(int[][] maze, int targetX, int targetY) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (i == targetY && j == targetX) {
                    System.out.print("@ ");
                } else {
                    if (maze[i][j] == 1) {
                        System.out.print("X ");
                    } else {
                        System.out.print("O ");
                    }
                }
            }
            System.out.println("");
        }
    }

}

class Position {
    int x, y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

/*
* Use ball to mark visited trace
* */
class MarkBall {
    int x,y;
    MarkBall father;

    MarkBall(int x, int y, MarkBall father) {
        this.x = x;
        this.y = y;
        this.father = father;
    }
}
