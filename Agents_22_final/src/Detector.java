import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Detector extends Agent {
    public static Node position;
    // aStar(position, target_pos)
    //
    public static  int energy;

    private String[] NumberOfAgentsArray;


    private Queue<Node> memory;

    public Detector() {
        this.position = position;
        this.energy = energy;

//        this.memory = new PriorityQueue<>((int) Math.sqrt(energy) / 2);
    }



    public void setup(){
        try{
            Thread.sleep(100);
        }
        catch(InterruptedException ie){

        }

        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("agent");
        template.addServices(sd);

        try{
            DFAgentDescription[] result = DFService.search(this, template);
//            NumberOfAgentsArray = new String[1];
//            NumberOfAgentsArray[0] = result[0].getName().getLocalName();
        }
        catch(FIPAException fe){
            fe.printStackTrace();
        }
//        System.out.println("I'm connecting with " +NumberOfAgentsArray[0]);
//        System.out.println("Initializing");
//        System.out.println("The world has 8x8 dimensions");
//        Game.initGrid();
//        Game.printGridContent();

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                try{
                    Thread.sleep(50);
                }
                catch(InterruptedException ie){
                }




//                List<Node> movesToExit = Game.aStar(position,Game.Exit);
//                    if(movesToExit.size() <= energy){
//                        int i =0;
//                        while(movesToExit != null){
//                            move(movesToExit.get(i));
//                            if(position.isExit()){
//
//                                //terminate
//                            }
//                            ++i;
//
//
//
//                        }
//
//
//                    }
//                    else{
//
//                        //lookaround()
//                        //if cleaner{ exchange memory}
//                        //move()
//
//                    }





            }
        });



    }


//    @Override
//    protected void setup() {
//        addBehaviour(new CyclicBehaviour() {
//            @Override
//            public void action() {
    // do a* and compare path list length to exit with current energy
    // if energy == path length list to exit
    // get list with nodes to exit from  a*
    // move to exit
    // return

    // if energy > path length to exit
    // lookAround and get list of near nodes
    // for each node, check if it is a cleaner
    // if yes, copy memory to cleaner
    // if not, check if dirty
    //
    // if yes, add it to memory (automatically forget oldest)
    // move to next random position from lookAround
//            }
//        });
//    }





    public void move(Node position) {
        this.energy--;
        switch (this.position.getContent()) {
            case 3 -> {
                position.setContent(1);
                Game.grid[this.position.getX()][this.position.getY()].setContent(1);
            }
            case 30 -> {
                position.setContent(0);
                Game.grid[this.position.getX()][this.position.getY()].setContent(0);
            }
            case 34 -> {
                position.setContent(4);
                Game.grid[this.position.getX()][this.position.getY()].setContent(4);
            }
        }


        switch (position.getContent()) {
            case 1 -> {
                position.setContent(3);
                Game.grid[position.getX()][position.getY()].setContent(3);
            }
            case 0 -> {
                position.setContent(30);
                Game.grid[position.getX()][position.getY()].setContent(30);
            }
            case 4 -> {
                position.setContent(34);
                Game.grid[position.getX()][position.getY()].setContent(34);
            }
        }
//        this.position = position;
        Game.printGridContent();
    }

    public Node getNextRandomPos() {
        List<Node> positions = new ArrayList<>();

        if (this.position.getX() - 1 > 0) {
            positions.add(Game.grid[position.getX() - 1][position.getY()]);
        }

        if (position.getX() + 1 < Game.SIZE) {
            positions.add(Game.grid[position.getX() + 1][position.getY()]);
        }

        if (position.getY() - 1 > 0) {
            positions.add(Game.grid[position.getX()][position.getY() - 1]);
        }

        if (position.getY() + 1 < Game.SIZE) {
            positions.add(Game.grid[position.getX()][position.getY() + 1]);
        }

        return positions.get(Game.PRNG.nextInt(4));
    }

    public List<Node> lookAround() {
        List<Node> positions = new ArrayList<>();

        // UP + 1
        if (this.position.getX() - 1 > 0) {
            positions.add(Game.grid[position.getX() - 1][position.getY()]);
        }

        // UP + 2
        if (this.position.getX() - 2 > 0) {
            positions.add(Game.grid[position.getX() - 1][position.getY()]);
        }

        // DOWN + 1
        if (position.getX() + 1 < Game.SIZE) {
            positions.add(Game.grid[position.getX() + 1][position.getY()]);
        }
        // DOWN + 2
        if (position.getX() + 2 < Game.SIZE) {
            positions.add(Game.grid[position.getX() + 1][position.getY()]);
        }

        // LEFT + 1
        if (position.getY() - 1 > 0) {
            positions.add(Game.grid[position.getX()][position.getY() - 1]);
        }
        // LEFT + 2
        if (position.getY() - 2 > 0) {
            positions.add(Game.grid[position.getX()][position.getY() - 1]);
        }

        // RIGHT + 1
        if (position.getY() + 1 < Game.SIZE) {
            positions.add(Game.grid[position.getX()][position.getY() + 1]);
        }
        // RIGHT + 2
        if (position.getY() + 1 < Game.SIZE) {
            positions.add(Game.grid[position.getX()][position.getY() + 1]);
        }

        return positions;
    }
}
