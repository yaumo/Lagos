# Lagos - Optimierung industrieller Prozesse 
#### Nico Himmelein, Philipp Thiele, Lukas Freitag, Thomas Richter

## Vorstellung der Aufgabe
- https://www.dropbox.com/s/3j60eoze10fiawi/SimBasedOptim.pdf?dl=0

## Docker
- https://hub.docker.com/r/jreichwald/dhbw_oip/
- [docker-compose.yml](Miscellaneous/docker-compose.yml)  
- Aufsetzen der Infrastruktur: docker-compose up -d in dem Order /Lagos/Miscellaneous

## Algorithmus: Simulated Annealing
### Dokumentation
Simualted Annealing ist ein Verfahren zum
Auffinden einer Näherungslösung für ein Optimierungsproblem
(meist Minimierung) ohne
dass alle Lösungen wie bei einem Brute-ForceAnsatz
ausprobiert werden müssen. Es ist eine
Heuristik, d.h. ein Verfahren das oft sehr gut
funktioniert, aber keine Garantie für eine optimale
oder auch nur gute Lösung gibt.

Es wird eine Random Search-Strategie
welche nicht nur Lösungen akzeptiert die
einen niedrigeren Funktionswert ergeben (im
Falle eines Minimierungsproblems), sondern
auch mit einer bestimmten Wahrscheinlichkeit
(Boltzmann-Gibbs-Verteilung) Lösungen die
einen höheren Funktionswert ergeben. Durch
diese Tatsache hat Simualted Annealing den
Vorteil dass lokalen Minima wieder verlassen
werden können.

Dieses Verfahren kann sowohl für kontinuierliche
Probleme der Art f : R
n → R als auch
für diskrete Probleme wie zB das Traveling Salesman
Problem angewandt werden. In diesem
Artikel werden beide Problemtypen einleitend
behandelt.

Simulated Annealing ist die Nachbildung eines
Wärmebehandlungsverfahrens der Metallurgie,
daher auch die englische Bezeichnung
Annealing.

Source: http://www.mycsharp.de/attachments/simulated_annealing.pdf

### Pseudocode
```
Wähle eine Anfangskonfiguration;  
Wähle eine Anfangstemperatur T > 0;  
wiederhole  
 wiederhole  
  Wähle eine neue Konfiguration, die eine kleine Änderung der alten Konfiguration ist;  
  Berechne die Energiefunktion der neuen Konfiguration;  
  DE := E(neu) - E(alt);  
  wenn DE < 0, dann alte_Konfiguration := neue_Konfiguration  
  sonst wenn Zufallszahl < e^-DE/kT,
   dann alte Konfiguration := neue Konfiguration  
 bis lange keine Erniedrigung der Energie;  
 Verringere T;  
bis keine Verringerung der Energiefunktion mehr auftritt;
```
Source: http://fbim.fh-regensburg.de/~saj39122/vhb/NN-Script/script/gen/k02070403.html


Optimum der Funktion:
-39,16617 * n < f(-2,903534,...) < -37,16616 * n
n=Tiefe an -2,903534en
