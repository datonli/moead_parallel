import matplotlib.pyplot as mpl

a,b = [], []

for line in open("/home/hadoop/experiment/serial_result/moead_new.txt").readlines(): 
    if line.__len__() != 1:
        a.append(line.strip().split(' ')[0])
        b.append(line.strip().split(' ')[1])

mpl.figure(1)
mpl.plot(a,b,'or')
a,b = [], []
for line in open("/home/hadoop/experiment/parallel_result/0.txt").readlines(): 
    if line.__len__() != 1:
        a.append(line.strip().split(' ')[0])
        b.append(line.strip().split(' ')[1])

#mpl.figure(2)
mpl.hold()
mpl.plot(a,b,':db')
a,b = [], []

for line in open("/home/hadoop/experiment/parallel_result/4.txt").readlines(): 
    if line.__len__() != 1:
        a.append(line.strip().split(' ')[0])
        b.append(line.strip().split(' ')[1])


mpl.plot(a,b,':db')

a,b = [], []
for line in open("/home/hadoop/experiment/parallel_result/8.txt").readlines(): 
    if line.__len__() != 1:
        a.append(line.strip().split(' ')[0])
        b.append(line.strip().split(' ')[1])


mpl.plot(a,b,':db')

a,b = [], []
for line in open("/home/hadoop/experiment/parallel_result/10.txt").readlines(): 
    if line.__len__() != 1:
        a.append(line.strip().split(' ')[0])
        b.append(line.strip().split(' ')[1])


mpl.plot(a,b,':db')

mpl.show()