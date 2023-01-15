import jade.core.AID;
import jade.core.Agent;
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

public class Cleaner extends Agent {
    protected static Node position;
    protected static  int energy;
    public static boolean firstMove = true;

    private Queue<Node> memory;

    public Cleaner() {
        this.position = position;
        this.energy = energy;

//        this.memory = new PriorityQueue<>((int) Math.sqrt(energy) / 2);
    }







    @Override
    public void setup() {

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("agent");
        sd.setName(getLocalName());
        dfd.addServices(sd);

        try{
            DFService.register(this, dfd);
        }
        catch(FIPAException fe){
            fe.printStackTrace();
        }



        // do a* and compare path list length to exit with current energy
        // if energy == path length list to exit
        // get list with nodes to exit from  a*
        // move to exit
        // return

        // check memory
        // if empty move to random spot --- move()
        // if not empty, get next dirty node
        // do a* and get path list to that dirty node from current position
        //                List<Node> pathToDirty = Game.aStar()
        //                for (int i = 0; i < pathToDirty.size(); i++) {
        //                    move(pathToDirty.get(i))
        // call clean to clean dirty node

    }


    public void clean(Node node) {
        node.setContent(4); // 1 is clean
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }



    public void move(Node position) {
        this.energy--;
        switch (this.position.getContent()) {
            case 4 -> {
                position.setContent(1);
                Game.grid[this.position.getX()][this.position.getY()].setContent(1);
            }
            case 40 -> {
                position.setContent(0);
                Game.grid[this.position.getX()][this.position.getY()].setContent(0);
            }
            case 34 -> {
                position.setContent(3);
                Game.grid[this.position.getX()][this.position.getY()].setContent(4);
            }
        }


        switch (position.getContent()) {
            case 1 -> {
                position.setContent(4);
                Game.grid[position.getX()][position.getY()].setContent(3);
            }
            case 0 -> {
                position.setContent(40);
                Game.grid[position.getX()][position.getY()].setContent(30);
            }
            case 3 -> {
                position.setContent(34);
                Game.grid[position.getX()][position.getY()].setContent(34);
            }
        }
        this.position = position;
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




}

