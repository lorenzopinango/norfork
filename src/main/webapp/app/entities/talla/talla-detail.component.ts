import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITalla } from 'app/shared/model/talla.model';

@Component({
  selector: 'jhi-talla-detail',
  templateUrl: './talla-detail.component.html'
})
export class TallaDetailComponent implements OnInit {
  talla: ITalla;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ talla }) => {
      this.talla = talla;
    });
  }

  previousState() {
    window.history.back();
  }
}
