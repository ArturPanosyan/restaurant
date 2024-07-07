import { Component, OnInit } from '@angular/core';
import { InventoryService } from './inventory.service';
import { Inventory } from './inventory.model';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  inventoryItems: Inventory[] = [];

  constructor(private inventoryService: InventoryService) { }

  ngOnInit(): void {
    this.loadInventoryItems();
  }

  loadInventoryItems(): void {
    this.inventoryService.getAllItems().subscribe(
      (data: Inventory[]) => {
        this.inventoryItems = data;
      },
      error => {
        console.error('Failed to fetch inventory items:', error);
      }
    );
  }

  addItem(itemName: string, quantity: number, location: string): void {
    const newItem: Inventory = { itemName, quantity, location } as Inventory;
    this.inventoryService.addItem(newItem).subscribe(
      (data: Inventory) => {
        console.log('Item added:', data);
        this.loadInventoryItems(); // Обновление списка инвентарных позиций после добавления нового
      },
      error => {
        console.error('Failed to add item:', error);
      }
    );
  }

  updateItemQuantity(itemId: number, quantity: number): void {
    this.inventoryService.updateItemQuantity(itemId, quantity).subscribe(
      (data: Inventory) => {
        console.log('Item quantity updated:', data);
        this.loadInventoryItems(); // Обновление списка инвентарных позиций после изменения количества
      },
      error => {
        console.error('Failed to update item quantity:', error);
      }
    );
  }

  deleteItem(itemId: number): void {
    this.inventoryService.deleteItem(itemId).subscribe(
      () => {
        console.log('Item deleted');
        this.loadInventoryItems(); // Обновление списка инвентарных позиций после удаления
      },
      error => {
        console.error('Failed to delete item:', error);
      }
    );
  }

  // Другие методы компонента для управления инвентаризацией
}
