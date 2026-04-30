package Control;

import Attachments.PlowHead;
import RoadComponents.*;
import Vehicles.*;

import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

public class Writer {
    private final Controller controller;
    private final FileWriter writer;

    public Writer(Controller controller, String filename) throws IOException{
        this.controller = controller;
        writer = new FileWriter(filename);
    }

    public void write() throws IOException {
        writer.write("newgame\n");
        writer.write(MessageFormat.format("mode {0}\n",controller.getDeterministic() ? "deterministic":"random"));

        for(Intersection i : controller.getIntersections()){
            writer.write(MessageFormat.format("create keresztezodes {0}\n",i.getId()));

            for(Road r : i.getRoads()){
                if(Objects.equals(r.getStartIntersectionId(), i.getId())){
                    writeRoad(r);
                }

                for(Lane l : r.getLanes()){
                    for(RoadSection rs : l.getAllRoadsectionsWithAccidents()){
                        writer.write(MessageFormat.format("setbaleset {0}\n",rs.getId()));
                    }

                    writeVehicles(l.getAllVehicles());
                }
            }

            writeVehicles(i.getVehicles());
        }

        writer.write(MessageFormat.format("tick {0}\n", controller.getTickCount()));

        writer.close();
    }

    private void writeRoad(Road r) throws IOException{
        writer.write(MessageFormat.format(
                "create ut {0} {1} {2} {3} {4} {5} {6} {7} {8} {9}\n",
                r.getId(),
                r.getStartIntersectionId(),
                r.getEndIntersectionId(),
                r.getLaneCount(),
                r.getLength(),
                r.getWay(),
                r.getSnowLevel(),
                r.getIceLevel(),
                r.getRockLevel(),
                r.getType().name().toLowerCase())
        );
    }

    private void writeVehicles(List<Vehicle> vehicles) throws IOException{
        for(Vehicle v : vehicles){
            Boolean atIntersection = v.getCurrIntersection() != null;
            String type;
            if(v instanceof Bus){
                type = "busz";
            }else if(v instanceof Car){
                type = "auto";
            }else if(v instanceof Snowplow){
                type = "hokotro";
            }else {
                continue;
            }

            writer.write(MessageFormat.format(
                    "create {0} {1} {2} {3}{4} {5}\n",
                    type,
                    v.getId(),
                    v.getStartIntersection().getId(),
                    v.getEndIntersection() != null ? v.getEndIntersection().getId()+" " : "",
                    atIntersection,
                    atIntersection ? v.getCurrIntersection().getId() : v.getCurrRoadSection().getId()
            ));

            writeUtvonal(v);

            if(type.equals("busz")){
                writeHolanc((Bus)v);
            }else if(type.equals("hokotro")){
                writeHokotro((Snowplow)v);
            }
        }
    }

    private void writeHolanc(Bus bus) throws IOException{
        writer.write(MessageFormat.format("attach holanc {0} {1}\n",
                bus.getId(),
                bus.getSnowchainTTL()));
    }

    private void writeHokotro(Snowplow snowplow) throws  IOException{
        for(PlowHead ph : snowplow.getPlowHeads()){
            writer.write(MessageFormat.format("attach fej {0} {1}\n", snowplow.getId(), ph.getClass().getName()));
            writer.write(MessageFormat.format("add consumable {0} {1} {2}\n", snowplow.getId(), ph.getConsumableAmountLeft(), ph.getClass().getName()));
        }

        writer.write(MessageFormat.format("setactivefej {0} {1}\n", snowplow.getId(), snowplow.getActivePlowHead().getClass().getName()));
    }

    private void writeUtvonal(Vehicle v) throws IOException{
        StringBuilder route = new StringBuilder();
        for(Intersection i : v.getJunctions()){
            route.append(i.getId()).append(" ");
        }

        writer.write(MessageFormat.format("setutvonal {0} {1}\n", v.getId(), route.toString().trim()));
    }
}
