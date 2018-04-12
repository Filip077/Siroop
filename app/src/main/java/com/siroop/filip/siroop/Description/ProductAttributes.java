package com.siroop.filip.siroop.Description;

/**
 * Created by filip on 15.2.18.
 */

public class ProductAttributes {
    private String name;
    private String brand;
    private String price;
    private String url ;
    private Image images;


    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }
    public String getPrice(){
        return price;
    }

    public String getUrl() {
        return url;
    }
        public Image getImage() {
            return images;
        }





    public class Image {
        private String highres;
        private String lowres;
        private String thumbnails;


        public String getHighres() {
            return highres;
        }

        public String getLowres() {
            return lowres;
        }

        public String getThumbnails() {
            return thumbnails;
        }


    }
}
