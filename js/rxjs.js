public doCreateBsp(): Observable<any> {
    return this.bitcoinSavingPlanService.getTrackingCode().pipe(
        takeUntil(this.destroy$),
        map((trackingCode) => {
            this.trackingCode = trackingCode

            return this.bitcoinSavingPlanService
                .create(
                    this.name,
                    BitcoinSavingPlanTypeUtils.of(this.storageChoice),
                    null,
                    null,
                    TransactionClientBankType.BANK_TRANSFER,
                    null,
                    trackingCode,
                    this.btcAddress,
                    null
                )
                .pipe(
                    map((bsp) => {
                        this.savingsAccountId = bsp.id
                        this.appService.bspNames.push(bsp)
                    })
                )
        }),
        concatAll()
    )
}
