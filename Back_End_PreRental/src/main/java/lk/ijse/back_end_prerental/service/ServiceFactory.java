package lk.ijse.back_end_prerental.service;



public class ServiceFactory {
    private static ServiceFactory boFactory;
    private ServiceFactory(){
    }
    public static ServiceFactory getBoFactory(){
        return (boFactory==null)? boFactory=new ServiceFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,PLACEORDER
    }


    public SuperBO getBO(BOTypes types){
        switch (types){
            /*case CUSTOMER :
                return new CustomerServiceImpl();*/

            /*case ITEM:
                return new ItemServiceImpl();

            case PLACEORDER:
                return new PlaceOrderServiceImpl();*/
        }

        return null;
    }
}
