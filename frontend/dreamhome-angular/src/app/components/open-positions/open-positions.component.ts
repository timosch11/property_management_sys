import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { OpenPosition } from 'src/app/interfaces/open-position';
import { environment } from 'src/environments/environment';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-open-positions',
  templateUrl: './open-positions.component.html',
  styleUrls: ['./open-positions.component.css']
})
export class OpenPositionsComponent implements OnInit {
  openPositions: OpenPosition[] = [];
  rawOpenPositions: OpenPosition[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.getOpenPositions();
  }

  private getOpenPositions(): void {
    this.http
      .get<any>(BACKEND_API_URL + '/open-positions')
      .subscribe((response) => {
        this.rawOpenPositions = response.openPositions;
        this.openPositions = this.rawOpenPositions;
      });
  }
  search(searchTerm: string): void {
    if (searchTerm.length == 0) {
      this.openPositions = this.rawOpenPositions;
    } else {
      this.openPositions = this.rawOpenPositions.filter(
        (openPosition) =>
        openPosition.renterId.toString() == searchTerm.trim() ||
        openPosition.rFirstname.toUpperCase() == searchTerm.trim().toUpperCase() ||
        openPosition.rLastname.toUpperCase() == searchTerm.trim().toUpperCase()
      );
    }
  }
}