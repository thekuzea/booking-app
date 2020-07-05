import { Component } from '@angular/core';
import { Profile } from "./shared/models/profile.model";
import { AuthenticationService } from './shared/services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  static AIRPORTS = [
    { code: "BPN", description: "Balikpapan, Indonesia (Sepinggan Int'l Airport - BPN)" },
    { code: "BDO", description: "Bandung, Indonesia (Husein Sastranegara Int'l Airport - BDO)" },
    { code: "BLR", description: "Bengaluru, India (Kempegowda International Airport, Bengaluru - BLR)" },
    { code: "CNS", description: "Cairns, Australia (Cairns International Airport - CNS)" },
    { code: "CEB", description: "Cebu, Philippines (Mactan Cebu Int'l Airport - CEB)" },
    { code: "CSX", description: "Changsha, China (Changsha Huanghua Int'l Airport - CSX)" },
    { code: "CTU", description: "Chengdu, China (Chengdu Shuangliu Int'l Airport - CTU)" },
    { code: "MAA", description: "Chennai, India (Chennai International Airport - MAA)" },
    { code: "CNX", description: "Chiang Mai, Thailand (Chiang Mai Int'l Airport - CNX)" },
    { code: "CKG", description: "Chongqing, China (Chongqing Jiangbei Int'l Airport - CKG)" },
    { code: "CJB", description: "Coimbatore, India (Coimbatore Int'l Airport - CJB)" },
    { code: "CMB", description: "Colombo, Sri Lanka (Bandaranaike Intl Airport - CMB)" },
    { code: "DAD", description: "Danang, Vietnam (Danang Int'l Airport - DAD)" },
    { code: "DRW", description: "Darwin, Australia (Darwin Int'l Airport - DRW)" },
    { code: "DVO", description: "Davao, Philippines (Francisco Bangoy Int'l Airport - DVO)" },
    { code: "DPS", description: "Denpasar (Bali), Indonesia (Ngurah Rai International Airport - DPS)" },
    { code: "DPS", description: "Fuzhou, China (Fuzhou Changle Intl Airport - FOC)" },
    { code: "FOC", description: "Denpasar (Bali), Indonesia (Ngurah Rai International Airport - DPS)" },
    { code: "OOL", description: "Gold Coast, Australia (Coolangatta - OOL)" },
    { code: "HAN", description: "Hanoi, Vietnam (Noi Bai Int'l Airport - HAN)" },
    { code: "HIJ", description: "Hiroshima, Japan (Hiroshima Airport - HIJ)" },
    { code: "HYD", description: "Hyderabad, India (Rajiv Gandhi Int'l Airport - HYD" },
    { code: "KLO", description: "Kalibo (Boracay), Philippines (Kalibo International Airport - KLO)" },
    { code: "KTM", description: "Kathmandu, Nepal (Tribhuvan Int'l Airport - KTM)" },
    { code: "COK", description: "Kochi, India (Cochin Int'l Airport - COK)" },
    { code: "USM", description: "Koh Samui, Thailand (Samui Int'l Airport - USM)" },
    { code: "CCU", description: "Kolkata, India (NSCB Int'l Airport - CCU)" },
    { code: "BKI", description: "Kota Kinabalu, Malaysia (Kota Kinabalu Int'l Airport - BKI)" },
    { code: "KUL", description: "Kuala Lumpur, Malaysia (Kuala Lumpur Int'l Airport - KUL)" },
    { code: "KMG", description: "Kunming, China (Kunming ChangShui Int'l Airport - KMG)" },
    { code: "LGK", description: "Langkawi, Malaysia (Langkawi Int'l Airport - LGK)" },
    { code: "LOP", description: "Lombok, Indonesia (Bandara Int'l Lombok - LOP)" },
    { code: "LPQ", description: "Luang Prabang, Laos (Luang Prabang International Airport - LPQ)" },
    { code: "UPG", description: "Makassar, Indonesia (Sultan Hasanuddin Int'l airport - UPG)" },
    { code: "MDC", description: "Manado, Indonesia (Sam Ratulangi Int'l Airport - MDC)" },
    { code: "MDL", description: "Mandalay, Myanmar (Mandalay International Airport - MDL)" },
    { code: "KNO", description: "Medan, Indonesia (Kuala Namu Int'l Airport - KNO)" },
    { code: "PKU", description: "Pekanbaru, Indonesia (Sultan Syarif Qasim II Int'l Airport - PKU)" },
    { code: "PEN", description: "Penang, Malaysia (Penang Int'l Airport - PEN)" },
    { code: "PNH", description: "Phnom Penh, Cambodia (Phnom Penh Int'l Airport - PNH)" },
    { code: "HKT", description: "Phuket, Thailand (Phuket Int'l Airport - HKT)" },
    { code: "CTS", description: "Sapporo, Japan (New Chitose - CTS)" },
    { code: "SRG", description: "Semarang, Indonesia (Achmad Yani Int'l Airport - SRG)" },
    { code: "SZX", description: "Shenzhen, China (Shenzhen Bao'an Int'l Airport - SZX)" },
    { code: "REP", description: "Siem Reap, Cambodia (Siem Reap Int'l Airport - REP)" },
    { code: "SIN", description: "Singapore, Singapore (Singapore Changi Int'l Airport - SIN)" },
    { code: "SUB", description: "Surabaya, Indonesia (Juanda Int'l Airport - SUB)" },
    { code: "TRV", description: "Trivandrum, India (Trivandrum Int'l Airport - TRV)" },
    { code: "VTE", description: "Vientiane, Laos (Vientiane Wattay International Airport - VTE)" },
    { code: "VTZ", description: "Visakhapatnam, India (Visakhapatnam International Airport - VTZ)" },
    { code: "WUH", description: "Wuhan, China (Wuhan Tianhe Int'l Airport - WUH)" },
    { code: "XMN", description: "Xiamen, China (Xiamen Gaoqi Int'l Airport - XMN)" },
    { code: "RGN", description: "Yangon, Myanmar (Yangon International Airport - RGN)" },
    { code: "JOG", description: "Yogyakarta, Indonesia (Adisucipto Int'l airport - JOG)" }
  ];

  constructor(private authenticationService: AuthenticationService) {
  }

}
