CC = gcc
CFLAGS = -Wall -c -O1 -lm
OBJDIR = bin
SRCDIR = src
OBJS = $(addprefix $(OBJDIR)/, test.o util.o insertSort.o mergeSort.o\
quickSort.o mergeInsert.o quickHybrid.o quickYaro.o countingSort.o\
radixSort.o)

all: sort
debug: CFLAGS += -g
debug: clean sort
sort: $(OBJS)
	gcc $(OBJS) -lm -o $(OBJDIR)/sort

$(OBJDIR)/%.o: $(SRCDIR)/%.c
	$(CC) $(CFLAGS) $< -c -o $@

$(OBJS): $(OBJDIR)

$(OBJDIR):
	mkdir $(OBJDIR)

clean:
	rm -f $(OBJDIR)
