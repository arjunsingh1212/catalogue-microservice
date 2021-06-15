package org.arjun.assignment1.reader;

import org.arjun.assignment1.item.ItemDTO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReaderItemCLI implements ReaderItem {

  @Override
  public ItemDTO read() {
    final ItemDTO itemDTO = new ItemDTO();
    final BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(System.in));
    try {
      System.out.print("Enter Name: ");
      itemDTO.setName(bufferedReader.readLine());
      System.out.print("Enter Price: ");
      itemDTO.setPrice(bufferedReader.readLine());
      System.out.print("Enter Quantity: ");
      itemDTO.setQuantity(bufferedReader.readLine());
      System.out.print("Enter Type: ");
      itemDTO.setType(bufferedReader.readLine());
    } catch (IOException exception) {
      System.out.println(exception.getMessage());
    }
    return itemDTO;
  }
}
