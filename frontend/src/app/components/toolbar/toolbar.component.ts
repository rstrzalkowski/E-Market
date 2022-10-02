import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  @Output() onSearchEvent: EventEmitter<string | null> = new EventEmitter<string | null>();
  @Output() onSortChangeEvent: EventEmitter<string | null> = new EventEmitter<string | null>();
  @Output() onPagePrevious: EventEmitter<string | null> = new EventEmitter<string | null>();
  @Output() onPageNext: EventEmitter<string | null> = new EventEmitter<string | null>();

  @Input() totalPagesObservable!: Observable<number>;
  @Input() actualPageObservable!: Observable<number>;

  searchControl = new FormControl('');
  sortControl = new FormControl('datedesc');

  totalPages: number = 1;
  actualPage: number = 0;

  constructor() {
  }

  ngOnInit(): void {
    this.searchControl.valueChanges.subscribe((keyword) => this.onSearch(keyword))

    this.sortControl.valueChanges.subscribe((change) => this.onSortChange(change))

    this.actualPageObservable.subscribe((page) => {
      this.actualPage = page;
    })

    this.totalPagesObservable.subscribe((pages) => {
      if (pages === 0) {
        this.totalPages = 1;
      }
      this.totalPages = pages;
    })
  }

  onSearch(keyword: string | null) {
    this.onSearchEvent.emit(keyword);
  }

  onSortChange(newSortMethod: string | null) {
    this.onSortChangeEvent.emit(newSortMethod);
    this.onSearch(this.searchControl.value);
  }

  previousPage() {
    if (this.actualPage > 0) {
      this.onPagePrevious.emit(this.searchControl.value);
    }
  }

  nextPage() {
    if (this.actualPage < this.totalPages - 1) {
      this.onPageNext.emit(this.searchControl.value);
    }
  }

}
