import { Component, OnInit } from '@angular/core';
import { MenuService } from './menu.service';
import { MenuItem } from './menu.item.model';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  standalone: true,
  styleUrls: ['./menu.component.css']
})
export class MenuListComponent implements OnInit {

  menuItems: MenuItem[] = [];

  constructor(private menuService: MenuService) { }

  ngOnInit(): void {
    this.loadMenuItems();
  }

  loadMenuItems(): void {
    this.menuService.getAllMenuItems().subscribe(
      (data: MenuItem[]) => {
        this.menuItems = data;
      },
      error => {
        console.error('Failed to fetch menu items:', error);
      }
    );
  }

  // Другие методы компонента, например, для добавления пункта меню
}
