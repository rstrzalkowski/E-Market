import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  @Output() onSearchEvent: EventEmitter<string | null> = new EventEmitter<string | null>();
  @Output() onSortChangeEvent: EventEmitter<string | null> = new EventEmitter<string | null>();

  searchControl = new FormControl('');
  sortControl = new FormControl('datedesc');

  constructor() {
  }

  ngOnInit(): void {
    this.searchControl.valueChanges.subscribe((keyword) => this.onSearch(keyword))
    this.sortControl.valueChanges.subscribe((change) => this.onSortChange(change))
  }

  onSearch(keyword: string | null) {
    this.onSearchEvent.emit(keyword)
    console.log(`Showing results for: ${keyword}`)
  }

  onSortChange(newSort: string | null) {
    this.onSortChangeEvent.emit(newSort);
    this.onSearch(this.searchControl.getRawValue())
    console.log(`Changing sort method to: ${newSort}`)
  }

}
