package br.com.hannasocial;

public class LocationDevice {
    private String _objId;
    private String _deviceId;
    private String _latitude;
    private String _longitude;
    private String _token;

    public LocationDevice() {

    }
    public LocationDevice(String deviceId, String latitude, String longitude,String objid, String token) {
        this._deviceId = deviceId;
        this._latitude = latitude;
        this._longitude = longitude;
        this._objId = objid;
        this._token = token;
    }

    public String get_token() {return _token;}

    public void set_token(String _token) {this._token = _token;}

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

    public String get_objId() { return _objId; }

    public void set_objId(String _objId) { this._objId = _objId; }
}
