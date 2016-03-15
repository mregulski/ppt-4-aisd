#ifndef LIST
#define LIST 1

#include <stdlib.h>
#include <stdbool.h>
#include <stdio.h>

// might make sense
typedef int list_val_t;

// list node
typedef struct sl_node {
    list_val_t value;
    struct sl_node *next;
} SNode;

typedef struct dl_node {
    list_val_t value;
    struct dl_node *prev;
    struct dl_node *next;
} DNode;

// list data
typedef struct {
    SNode *first;
    int count;
} List;

typedef struct {
    DNode *first;
    int count;
} DList;

// create new separated node with value
SNode *snode(list_val_t value);
DNode *dnode(list_val_t value);
// create new empty list
List *l_new();

// true if list contains no elements
bool l_isEmpty(List *list);

// true if value occurs in list
bool l_find(List *list, list_val_t value);

// insert value into list at any index
void l_insert(List *list, int index, list_val_t value);

// get value of node at index
list_val_t l_fetch(List *list, int index);

// insert value into index = 0
void l_insertStart(List *list, list_val_t value);

// insert value into index = list.count
void l_insertEnd(List *list, list_val_t value);

// remove element at index
void l_remove(List *list, int index);

// free all nodes in list
void l_free(List *list);

// print values in list to stdout
void l_print(List *list);

// append list2 to list1
void l_merge(List *list1, List *list2);

#endif
