package com.zackyzhang.chinesefoodnearme.data.entity;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by lei on 3/28/17.
 */
@Parcel
public class SearchResponse {

    /**
     * total : 8228
     * businesses : [{"rating":4,"price":"$","phone":"+14152520800","id":"four-barrel-coffee-san-francisco","is_closed":false,"categories":[{"alias":"coffee","title":"Coffee & Tea"}],"review_count":1738,"name":"Four Barrel Coffee","url":"https://www.yelp.com/biz/four-barrel-coffee-san-francisco","coordinates":{"latitude":37.7670169511878,"longitude":-122.42184275},"image_url":"http://s3-media2.fl.yelpcdn.com/bphoto/MmgtASP3l_t4tPCL1iAsCg/o.jpg","location":{"city":"San Francisco","country":"US","address2":"","address3":"","state":"CA","address1":"375 Valencia St","zip_code":"94103"},"distance":1604.23,"transactions":["pickup","delivery"]}]
     * region : {"center":{"latitude":37.767413217936834,"longitude":-122.42820739746094}}
     */

    @SerializedName("total")
    int total;
    @SerializedName("region")
    RegionBean region;
    @SerializedName("businesses")
    List<Business> businesses;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public RegionBean getRegion() {
        return region;
    }

    public void setRegion(RegionBean region) {
        this.region = region;
    }

    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

    @Parcel
    public static class RegionBean {
        /**
         * center : {"latitude":37.767413217936834,"longitude":-122.42820739746094}
         */

        @SerializedName("center")
        CenterBean center;

        public CenterBean getCenter() {
            return center;
        }

        public void setCenter(CenterBean center) {
            this.center = center;
        }

        @Parcel
        public static class CenterBean {
            /**
             * latitude : 37.767413217936834
             * longitude : -122.42820739746094
             */

            @SerializedName("latitude")
            double latitude;
            @SerializedName("longitude")
            double longitude;

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }
        }
    }

}
