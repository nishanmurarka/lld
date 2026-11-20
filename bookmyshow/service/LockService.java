package bookmyshow.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import bookmyshow.models.*;

public class LockService {

    Map<ShowSeat, SeatLock> map;

    public LockService(){
        map=new ConcurrentHashMap<>();
    }

    public synchronized boolean isLocked(ShowSeat showSeat) {
    
        SeatLock lock = map.get(showSeat);
        if(lock !=null)
        {
            if(System.currentTimeMillis()<lock.getExpiry())
                return true;
        }
        return false;
    }

    public synchronized void lock(String user, ShowSeat showSeat){
        try{    
            SeatLock lock = map.get(showSeat);
            if(lock !=null)
            {
                if( System.currentTimeMillis()<lock.getExpiry())
                    throw new Exception("Seat Already Locked");
            }
            map.put(showSeat, new SeatLock(showSeat, user, 60_000));
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

    }

    public synchronized void unlock(String user, ShowSeat showSeat)
    {
        try {
            SeatLock lock=map.get(showSeat);
            if(lock==null)
                return;
            if(!lock.getLockedBy().equalsIgnoreCase(user))
                throw new Exception("Can be unlocked only by the same User");

            map.remove(showSeat);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
