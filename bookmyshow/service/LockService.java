package bookmyshow.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import bookmyshow.models.*;

public class LockService {

    Map<ShowSeat, SeatLock> map;
    Map<ShowSeat, Object> showSeatLockObject;

    public LockService(){
        map=new ConcurrentHashMap<>();
        showSeatLockObject = new ConcurrentHashMap<>();
    }

    public Object getLockObject(ShowSeat seat){
        return showSeatLockObject.computeIfAbsent(seat, key -> new Object());
    }

    public boolean validateLock(String user, ShowSeat showSeat) {
        synchronized(getLockObject(showSeat)){
            SeatLock lock = map.get(showSeat);
            if(lock !=null)
            {
                if(System.currentTimeMillis()<lock.getExpiry())
                    return lock.getLockedBy().equalsIgnoreCase(user);
            }
            return false;
        }
    }

    public boolean isLocked(ShowSeat showSeat) {
            SeatLock lock = map.get(showSeat);
            return lock !=null && System.currentTimeMillis()<lock.getExpiry();
        
    }

    public void lock(String user, ShowSeat showSeat){
        synchronized(getLockObject(showSeat)){
            if(isLocked(showSeat))
                throw new RuntimeException("Seat Already Locked");

            map.put(showSeat, new SeatLock(showSeat, user, 10));
        }
    }

    public void unlock(String user, ShowSeat showSeat)
    {
        synchronized(getLockObject(showSeat)){
            SeatLock lock=map.get(showSeat);
            if(lock==null)
                return;
            if(!lock.getLockedBy().equalsIgnoreCase(user))
                throw new RuntimeException("Can be unlocked only by the same User");
            map.remove(showSeat);
        }
    }
}
