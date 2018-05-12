package uk.ac.cam.interaction_design.group02.hiking_app.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class used to share a single set of hikes through modules
 */
public class HikeManager {
    private List<Hike> hikes;
    private static HikeManager instance;

    /**
     * @return List of hikes
     */
    public List<Hike> getHikes() {
        return hikes;
    }

    /**
     * @param hike New hike to be added to the list
     */
    public void addHike(Hike hike) {
        hikes.add(hike);
    }

    private HikeManager(){
        hikes = new ArrayList<>();
    }

    /**
     * Singleton method to get the instance of HikeManager
     * @return HikeManager singleton
     */
    public static HikeManager getInstance() {
        if(instance == null) {
            instance = new HikeManager();
        }
        return instance;
    }
}
