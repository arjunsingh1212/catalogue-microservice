package org.arjun.assignment1.writer;

import org.arjun.assignment1.item.ItemEntity;
import java.math.BigDecimal;

public class WriterItemCLI implements WriterItem {
  @Override
  public void write(ItemEntity item) {
    System.out.println(item.getName()
            + ", " + item.getPrice()
            + ", " + item.getTax()
            + ", " + item.getQuantity()
            + ", " + BigDecimal.valueOf(item.getQuantity()).multiply(
                    item.getPrice().add(item.getTax())));
  }
}
