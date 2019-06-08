package br.com.hannasocial;

public class LocationDevice {
    public LocationDevice() {

    }
    public LocationDevice(String deviceId, String latitude, String longitude) {
        this._deviceId = deviceId;
        this._latitude = latitude;
        this._longitude = longitude;
    }

    public String get_deviceId() {
        return _deviceId;
    }

    public void set_deviceId(String deviceId) {
        this._deviceId = deviceId;
    }

    public String get_latitude() {
        return _latitude;
    }

    public void set_latitude(String latitude) {
        this._latitude = latitude;
    }

    public String get_longitude() {
        return _longitude;
    }

    public void set_longitude(String longitude) {
        this._longitude = longitude;
    }

    private String _deviceId;
    private String _latitude;
    private String _longitude;
}
