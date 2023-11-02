package StoreManagement.notificationManager;

import StoreManagement.inventoryManagement.StoreInventory;
import org.springframework.stereotype.Service;

@Service
public class EmailBuilder {
    public static String emailBuilderForThresholdNotification(StoreInventory storeInventory) {

        String message = "The available stock has fallen below the minimum threshold.";

        if (storeInventory.getQuantity() > storeInventory.getMaxThreshold())
            message = "The available stock has exceeded the maximum threshold.";

        return "\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            margin: 50px;\n" +
                "        }\n" +
                "        .header {\n" +
                "            background-color: #FF6B6B; \n" +
                "            color: #ffffff;\n" +
                "            text-align: center;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .header h1 {\n" +
                "            font-size: 28px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            background-color: #f0f0f0;\n" +
                "            text-align: center;\n" +
                "            padding: 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"header\">\n" +
                "        <h1>" + message + "</h1>\n" +
                "    </div>\n" +
                "    <div>\n" +
                "        <h1>Store inventory detail:</h1>\n" +
                "        <ul>\n" +
                "            <li>Store Name:" + storeInventory.getStore().getStoreName() + "</li>\n" +
                "            <li>Store Id:" + storeInventory.getStore().getStoreId() + "</li>\n" +
                "            <li>Item Name:" + storeInventory.getItem().getItemName() + "</li>\n" +
                "            <li>Current Stock:" + storeInventory.getQuantity() + "</li>\n" +
                "            <li>Max-threshold:" + storeInventory.getMaxThreshold() + "</li>\n" +
                "            <li>Min-threshold:" + storeInventory.getMinThreshold() + "</li>\n" +
                "        </ul>\n" +
                "        <p style=\"font-size: 16px; margin: 20px 0;\">This may require immediate attention to manage your inventory effectively. Please review and take appropriate action as necessary.</p>\n" +
                "    </div>\n" +
                "    <div class=\"footer\">\n" +
                "        <p>Store Management</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}
