package com.zackyzhang.chinesefoodnearme.network.entity;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by lei on 3/29/17.
 */
@Parcel
public class Business {
    /**
     * rating : 4
     * price : $
     * phone : +14152520800
     * id : four-barrel-coffee-san-francisco
     * is_closed : false
     * categories : [{"alias":"coffee","title":"Coffee & Tea"}]
     * review_count : 1738
     * name : Four Barrel Coffee
     * url : https://www.yelp.com/biz/four-barrel-coffee-san-francisco
     * coordinates : {"latitude":37.7670169511878,"longitude":-122.42184275}
     * image_url : http://s3-media2.fl.yelpcdn.com/bphoto/MmgtASP3l_t4tPCL1iAsCg/o.jpg
     * location : {"city":"San Francisco","country":"US","address2":"","address3":"","state":"CA","address1":"375 Valencia St","zip_code":"94103"}
     * distance : 1604.23
     * transactions : ["pickup","delivery"]
     */

    @SerializedName("rating")
    double rating;
    @SerializedName("price")
    String price;
    @SerializedName("phone")
    String phone;
    @SerializedName("id")
    String id;
    @SerializedName("is_closed")
    boolean isClosed;
    @SerializedName("review_count")
    int reviewCount;
    @SerializedName("name")
    String name;
    @SerializedName("url")
    String url;
    @SerializedName("coordinates")
    CoordinatesBean coordinates;
    @SerializedName("image_url")
    String imageUrl;
    @SerializedName("location")
    LocationBean location;
    @SerializedName("distance")
    double distance;
    @SerializedName("categories")
    List<CategoriesBean> categories;
    @SerializedName("transactions")
    List<String> transactions;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CoordinatesBean getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesBean coordinates) {
        this.coordinates = coordinates;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }

    @Parcel
    public static class CoordinatesBean {
        /**
         * latitude : 37.7670169511878
         * longitude : -122.42184275
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

    @Parcel
    public static class LocationBean {
        /**
         * city : San Francisco
         * country : US
         * address2 :
         * address3 :
         * state : CA
         * address1 : 375 Valencia St
         * zip_code : 94103
         */

        @SerializedName("city")
        String city;
        @SerializedName("country")
        String country;
        @SerializedName("address2")
        String address2;
        @SerializedName("address3")
        String address3;
        @SerializedName("state")
        String state;
        @SerializedName("address1")
        String address1;
        @SerializedName("zip_code")
        String zipCode;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getAddress3() {
            return address3;
        }

        public void setAddress3(String address3) {
            this.address3 = address3;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }

    @Parcel
    public static class CategoriesBean {
        /**
         * alias : coffee
         * title : Coffee & Tea
         */

        @SerializedName("alias")
        String alias;
        @SerializedName("title")
        String title;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
