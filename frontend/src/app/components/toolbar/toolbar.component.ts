import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  @Output() onSearchEvent: EventEmitter<string | null> = new EventEmitter<string | null>();
  searchControl = new FormControl('');

  constructor() {
  }

  ngOnInit(): void {
    this.searchControl.valueChanges.subscribe((keyword) => this.onSearch(keyword))
  }

  onSearch(keyword: string | null) {
    this.onSearchEvent.emit(keyword)
    console.log(`Showing results for: ${keyword}`)
  }

}
