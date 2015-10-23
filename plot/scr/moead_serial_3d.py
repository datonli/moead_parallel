'''
Created on May 11, 2015

@author: root
'''
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d.axes3d import Axes3D 

x,y,z = [], [], []
#for line in open("/home/hadoop/experiment/serial_result/moead_ZDT3_30.txt"):
for line in open("/home/hadoop/experiment/serial_result/moead_new.txt").readlines(): 
    if line.__len__() != 1:
        lineSplit = line.strip().split(' ')
        x.append(float(lineSplit[0]))
        y.append(float(lineSplit[1]))
        z.append(float(lineSplit[2]))


# fig = plt.figure()
# ax = fig.gca(projection='3d')
# ax.plot(a,b,c,label='curve')

# ax=plt.subplot(111,projection='3d')
# ax.plot_surface(x,y,z,rstride=2,cstride=1,cmap=plt.cm.Blues_r)

fig = plt.figure(1)
ax = fig.add_subplot(111,projection='3d')
ax.scatter(x,y,z,c='r',marker='o')
ax.set_xlabel("X")
ax.set_ylabel("Y")
ax.set_zlabel("Z")
plt.show()