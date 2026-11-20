package bookmyshow.models;

public class SeatLock {
    private ShowSeat showSeat;
    private String lockedBy;
    private long expiry;

    public SeatLock(ShowSeat showSeat, String lockedBy, long timeOutInSeconds) {
        this.showSeat = showSeat;
        this.lockedBy = lockedBy;
        this.expiry = System.currentTimeMillis() + (timeOutInSeconds * 1000);
    }

    public boolean isExpired() {
        return  System.currentTimeMillis() >expiry;
    }

    public ShowSeat getShowSeat() {
        return showSeat;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public long getExpiry() {
        return expiry;
    }
}
