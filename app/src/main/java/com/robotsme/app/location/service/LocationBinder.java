package com.robotsme.app.location.service;

import android.os.Binder;

public class LocationBinder extends Binder {

    private LocationService service;

    public LocationBinder(LocationService locationService) {
        this.service = locationService;
    }

    public LocationService getService() {
        return service;
    }
}
