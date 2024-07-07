import { Component, OnInit } from '@angular/core';
import { Dish } from './dish.model';
import { MenuService } from './menu.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  standalone: true,
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  dishes: Dish[] = [];

  constructor(private menuService: MenuService) { }

  ngOnInit(): void {
    this.loadDishes();
  }

  loadDishes(): void {
    this.menuService.getDishesByCategory('Main').subscribe(
      (data) => {
        this.dishes = data;
      },
      (error) => {
        console.error('Error loading dishes: ', error);
      }
    );
  }

  searchDishes(name: string): void {
    this.menuService.searchDishesByName(name).subscribe(
      (data) => {
        this.dishes = data;
      },
      (error) => {
        console.error('Error searching dishes: ', error);
      }
    );
  }
}
