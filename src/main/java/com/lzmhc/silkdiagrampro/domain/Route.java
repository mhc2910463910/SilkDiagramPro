package com.lzmhc.silkdiagrampro.domain;

import java.util.List;

public class Route {
    private int RouteId;
    private String RouteName;
    private String RouteText;
    private String RouteImg;
    private double RoutePrice;
    private String RoutePosition;

    public int getRouteId() {
        return RouteId;
    }

    public void setRouteId(int routeId) {
        RouteId = routeId;
    }

    public String getRouteName() {
        return RouteName;
    }

    public void setRouteName(String routeName) {
        RouteName = routeName;
    }

    public String getRouteText() {
        return RouteText;
    }

    public void setRouteText(String routeText) {
        RouteText = routeText;
    }

    public String getRouteImg() {
        return RouteImg;
    }

    public void setRouteImg(String routeImg) {
        RouteImg = routeImg;
    }

    public double getRoutePrice() {
        return RoutePrice;
    }

    public void setRoutePrice(double routePrice) {
        RoutePrice = routePrice;
    }

    public String getRoutePosition() {
        return RoutePosition;
    }

    public void setRoutePosition(String routePosition) {
        RoutePosition = routePosition;
    }

    @Override
    public String toString() {
        return "Route{" +
                "RouteId=" + RouteId +
                ", RouteName='" + RouteName + '\'' +
                ", RouteText='" + RouteText + '\'' +
                ", RouteImg=" + RouteImg +
                ", RoutePrice=" + RoutePrice +
                ", RoutePosition='" + RoutePosition + '\'' +
                '}';
    }
}
