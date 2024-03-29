'''
Created on May 11, 2015

@author: root
'''

import numpy as np
import mpl_toolkits.mplot3d
import matplotlib.pyplot as plt

x,y=np.mgrid[-2:2:20j,-2:2:20j]
z=x*np.exp(-x**2-y**2)
ax=plt.subplot(111,projection='3d')
# ax.plot_surface(x,y,z,rstride=2,cstride=1,cmap=plt.cm.Blues_r)
ax.plot_surface(x,y,z)
plt.show()