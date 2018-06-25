package mk.ukim.finki.mpip.helloworld.tasks;

import java.util.List;

import mk.ukim.finki.mpip.helloworld.model.CelestialBodyListItem;
import mk.ukim.finki.mpip.helloworld.model.Universe;

/**
 * Created by Dejan on 14.11.2017.
 */

public interface ICelestialBodiesCallbacks
{
    void setListData(List<CelestialBodyListItem> celestialBodies);
}
