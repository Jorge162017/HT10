import networkx as nx
import os

def read_graph():
    G = nx.DiGraph()
    with open('logistica.txt', 'r') as file:
        for line in file:
            ciudad1, ciudad2, tiempoNormal, tiempoLluvia, tiempoNieve, tiempoTormenta = line.strip().split()
            G.add_edge(ciudad1, ciudad2, weight=float(tiempoNormal))

    return G


def calculate_shortest_path(G, origen, destino):
    predecesores, _ = nx.floyd_warshall_predecessor_and_distance(G)

    if origen not in predecesores or destino not in predecesores[origen]:
        return "No existe ruta desde {} a {}".format(origen, destino)

    path = [destino]
    while destino != origen:
        destino = predecesores[origen][destino]
        path.append(destino)
    path.reverse()

    return path


def find_center(G):
    center = nx.center(G.to_undirected(), usebounds=True)
    return center


def update_graph(G):
    print("Introduce las modificaciones en el formato 'ciudad_origen ciudad_destino tiempo_normal tiempo_lluvia tiempo_nieve tiempo_tormenta'")
    line = input()
    ciudad1, ciudad2, tiempoNormal, tiempoLluvia, tiempoNieve, tiempoTormenta = line.strip().split()
    G.add_edge(ciudad1, ciudad2, weight=float(tiempoNormal))
    return G


def main():
    G = read_graph()
    while True:
        print('1. Calcular la ruta más corta entre dos ciudades.')
        print('2. Encontrar el centro del grafo.')
        print('3. Modificar el grafo.')
        print('4. Salir.')
        option = input()
        if option == '1':
            clear();
            origen = input('Ciudad origen: ')
            destino = input('Ciudad destino: ')
            path = calculate_shortest_path(G, origen, destino)
            print('Ruta más corta: ', path)
            print()
        elif option == '2':
            clear();
            center = find_center(G)
            print('Centro del grafo: ', center)
        elif option == '3':
            clear();
            G = update_graph(G)
        elif option == '4':
            break
        else:
            clear();
            print('Opción no válida.')

def clear():
    if os.name == "nt":
        os.system("cls")
    else:
        os.system("clear")

if __name__ == "__main__":
    main()
