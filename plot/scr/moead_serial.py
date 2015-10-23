'''
Created on May 11, 2015

@author: root
'''
import matplotlib.pyplot as plt

a,b,c = [], [], []
#for line in open("/home/hadoop/experiment/serial_result/moead_ZDT3_30.txt"):
# for line in open("/home/hadoop/experiment/serial_result/moead_new.txt").readlines(): 
for line in open("/home/laboratory/workspace/moead_parallel/experiments/moead_new.txt").readlines(): 
    if line.__len__() != 1:
        lineSplit = line.strip().split(' ')
        a.append(float(lineSplit[0]))
        b.append(float(lineSplit[1]))
plt.figure(1)
plt.plot(a,b,'or')
plt.show()
