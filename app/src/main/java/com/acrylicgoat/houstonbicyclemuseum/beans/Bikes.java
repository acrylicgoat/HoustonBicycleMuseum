/**
 * Copyright (c) 2016 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.houstonbicyclemuseum.beans;

import java.util.ArrayList;

public class Bikes
{
    ArrayList<Bike> bikes;

    public Bike getBike(int position)
    {
        return bikes.get(position);
    }

    public ArrayList<Bike> getBikes()
    {
        return bikes;
    }
}
